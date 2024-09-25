package com.example.moscowcommerce_backend.Shared.Application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service
public class StorageImage {
    private final RestTemplate restTemplate;

    @Value("${imgur.client-id}")
    private String clientId;

    public StorageImage(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

     /**
     * Handles the uploading of a list of images to Imgur.
     *
     * @param images A list of MultipartFile objects representing the images to be uploaded.
     * @return A list of URLs of the uploaded images.
     */
    public List<String> HandleImage(List<MultipartFile> images) {
        // Initialize an empty list to store the URLs of the uploaded images
        List<String> photos = new ArrayList<>();

        // Iterate over each image in the provided list
        images.forEach(image -> {
            try {
                // Check if the image is empty
                if (image.isEmpty()) {
                    // Throw an exception if the image is empty
                    throw new Exception("Image is empty");
                } else {
                    // Create HTTP headers and set the content type to multipart/form-data
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    // Set the authorization header with the client ID
                    headers.set("Authorization", "Client-ID " + clientId);

                    // Create an HTTP entity with the headers and an empty body
                    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);
                    // Get the body of the request
                    MultiValueMap<String, Object> body = request.getBody();
                    // Add the image resource to the body
                    body.add("image", image.getResource());
                    // Add the content type of the image to the body
                    body.add("type", image.getContentType());
                    // Add the original filename of the image to the body
                    body.add("title", image.getOriginalFilename());
                    // Add a description to the body
                    body.add("description", "Image uploaded by Moscow Commerce");

                    // Send a POST request to the Imgur API to upload the image and get the response
                    Map<String, Object> response = restTemplate.exchange("https://api.imgur.com/3/image", HttpMethod.POST, request, Map.class).getBody();
                    // Extract the URL of the uploaded image from the response and add it to the list
                    photos.add(((Map<String, Object>) response.get("data")).get("link").toString());
                }
            } catch (Exception e) {
                // Print the stack trace if an exception occurs
                e.printStackTrace();
            }
        });
        // Return the list of URLs of the uploaded images
        return photos;
    }
}
