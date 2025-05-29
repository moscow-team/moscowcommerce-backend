package com.example.moscowcommerce_backend.Auth.Infrastructure.Security;

import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.InvalidTokenException;
import com.example.moscowcommerce_backend.Auth.Domain.Exceptions.UnauthorizedException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  public JwtAuthenticationFilter(
      JwtService jwtService,
      UserDetailsService userDetailsService,
      HandlerExceptionResolver handlerExceptionResolver) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    final String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = authHeader.substring(7);
      final String userEmail = this.extractName(jwt);

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if (userEmail != null && authentication == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

        if (!jwtService.isTokenValid(jwt, userDetails)) {
          response.setStatus(HttpStatus.UNAUTHORIZED.value());
          response.getWriter().write("Token inválido");
          return;
        }

        UsernamePasswordAuthenticationToken authToken =
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
      }

      filterChain.doFilter(request, response);
    } catch (InvalidTokenException e) {
      throw e;
    } catch (Exception exception) {
      throw new UnauthorizedException();
    }
  }

  private String extractName(String jwt) {
    try {
      return jwtService.extractUsername(jwt);
    } catch (Exception e) {
      throw new InvalidTokenException("Token invalido: " + jwt);
    }
  }
}
