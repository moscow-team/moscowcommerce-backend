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

    public List<String> HandleImage(List<MultipartFile> images) {
        List<String> photos = new ArrayList<>();
        images.forEach(image -> {
            try {
                if (image.isEmpty()) {
                    throw new Exception("Image is empty");
                } else {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
                    headers.set("Authorization", "Client-ID " + clientId);

                    // Create Request
                    HttpEntity<MultiValueMap<String, Object>> request = new HttpEntity<>(new LinkedMultiValueMap<>(), headers);
                    MultiValueMap<String, Object> body = request.getBody();
                    body.add("image", image.getResource());
                    body.add("type", image.getContentType());
                    body.add("title", image.getOriginalFilename());
                    body.add("description", "Image uploaded by Moscow Commerce");

                    Map<String, Object> response = restTemplate.exchange("https://api.imgur.com/3/image", HttpMethod.POST, request, Map.class).getBody();
                    photos.add(((Map<String, Object>) response.get("data")).get("link").toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return photos;
    }

}
