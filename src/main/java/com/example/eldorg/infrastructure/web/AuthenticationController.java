package com.example.eldorg.infrastructure.web;

import static com.example.eldorg.infrastructure.common.RestResponse.successfulResponse;

import com.example.eldorg.application.dto.AuthenticationRequest;
import com.example.eldorg.application.dto.RefreshTokenRequest;
import com.example.eldorg.application.dto.RegisterRequest;
import com.example.eldorg.application.service.AuthenticationService;
import com.example.eldorg.infrastructure.common.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
  private final AuthenticationService authenticationService;

  public AuthenticationController(AuthenticationService authenticationService) {
    this.authenticationService = authenticationService;
  }

  @PostMapping("/login")
  public ResponseEntity<RestResponse> login(@RequestBody AuthenticationRequest request) {
    return successfulResponse(authenticationService.authenticate(request));
  }

  @PostMapping("/register")
  public ResponseEntity<RestResponse> register(@RequestBody RegisterRequest request) {
    return successfulResponse(authenticationService.register(request));
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<RestResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
    return successfulResponse(authenticationService.getNewAccessToken(request.refreshToken()));
  }

  @PostMapping("/logout")
  public ResponseEntity<RestResponse> logout(@RequestBody RefreshTokenRequest request) {
    authenticationService.logout(request.refreshToken());
    return successfulResponse(null);
  }
}
