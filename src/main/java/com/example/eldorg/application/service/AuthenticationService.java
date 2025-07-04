package com.example.eldorg.application.service;

import static com.example.eldorg.infrastructure.common.RestResponse.unauthorizedExceptionResponse;

import com.example.eldorg.application.dto.AuthenticationRequest;
import com.example.eldorg.application.dto.AuthenticationResponse;
import com.example.eldorg.application.dto.RegisterRequest;
import com.example.eldorg.domain.model.User;
import com.example.eldorg.domain.model.UserRole;
import com.example.eldorg.domain.model.UserStatus;
import com.example.eldorg.domain.repo.UserRepo;
import com.example.eldorg.infrastructure.security.CustomUserDetails;
import com.example.eldorg.infrastructure.security.CustomUserDetailsService;
import com.example.eldorg.infrastructure.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final CustomUserDetailsService userDetailsService;

  public AuthenticationService(
      UserRepo userRepo,
      PasswordEncoder passwordEncoder,
      JwtService jwtService,
      AuthenticationManager authenticationManager,
      CustomUserDetailsService userDetailsService) {
    this.userRepo = userRepo;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
    this.userDetailsService = userDetailsService;
  }

  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.username(), request.password()));

    CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

    var accessToken = jwtService.generateToken(userDetails);
    var refreshToken = jwtService.generateRefreshToken(userDetails);

    return new AuthenticationResponse(accessToken, refreshToken);
  }

  public AuthenticationResponse register(RegisterRequest request) {
    var user =
        new User(
            request.username(),
            request.email(),
            passwordEncoder.encode(request.password()),
            UserRole.USER.name(),
            UserStatus.PENDING.name());
    userRepo.save(user);

    CustomUserDetails userDetails = new CustomUserDetails(user);
    var accessToken = jwtService.generateToken(userDetails);
    var refreshToken = jwtService.generateRefreshToken(userDetails);

    return new AuthenticationResponse(accessToken, refreshToken);
  }

  public AuthenticationResponse getNewAccessToken(String refreshToken) {
    var username = jwtService.extractUsername(refreshToken);
    var userDetails = userDetailsService.loadUserByUsername(username);
    var user = userDetails.getUser();
    validateToken(refreshToken, user);
    var newAccessToken = jwtService.generateToken(userDetails);
    return new AuthenticationResponse(newAccessToken, refreshToken);
  }

  private void validateToken(String token, User user) {
    if (!jwtService.isTokenValid(token, user.getUsername())) {
      throw unauthorizedExceptionResponse("Invalid refresh token").get();
    }
    if (!jwtService.isRefreshTokenRevoked(token, user.getId())) {
      throw unauthorizedExceptionResponse("Refresh token has been revoked").get();
    }
  }

  public void logout(String token) {
    var username = jwtService.extractUsername(token);
    var userDetails = userDetailsService.loadUserByUsername(username);
    var user = userDetails.getUser();
    jwtService.revokeRefreshToken(token, user.getId());
  }
}
