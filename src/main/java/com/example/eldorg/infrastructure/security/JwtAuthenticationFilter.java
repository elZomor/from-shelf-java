package com.example.eldorg.infrastructure.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtService jwtService;
  private final CustomUserDetailsService userDetailsService;

  public JwtAuthenticationFilter(
      JwtService jwtService, CustomUserDetailsService userDetailsService) {
    this.jwtService = jwtService;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    // Use Ant-style path matching for public endpoints
    return path.startsWith("/api/v1/auth/");
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    String authHeader = request.getHeader("Authorization");

    if (isInvalidHeader(authHeader)) {
      filterChain.doFilter(request, response);
      return;
    }

    String jwt = extractJwt(authHeader);
    String username = jwtService.extractUsername(jwt);

    if (shouldAuthenticate(username)) {
      authenticateUser(jwt, username, request);
    }

    filterChain.doFilter(request, response);
  }

  private boolean isInvalidHeader(String authHeader) {
    return authHeader == null || !authHeader.startsWith("Bearer ");
  }

  private String extractJwt(String authHeader) {
    return authHeader.substring(7);
  }

  private boolean shouldAuthenticate(String username) {
    return username != null && SecurityContextHolder.getContext().getAuthentication() == null;
  }

  private void authenticateUser(String jwt, String username, HttpServletRequest request) {
    CustomUserDetails userDetails = userDetailsService.loadUserByUsername(username);
    var user = userDetails.getUser();
    if (jwtService.isTokenValid(jwt, user.getUsername())) {
      UsernamePasswordAuthenticationToken authToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authToken);
    }
  }
}
