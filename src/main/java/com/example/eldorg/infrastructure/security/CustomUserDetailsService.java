package com.example.eldorg.infrastructure.security;

import static com.example.eldorg.infrastructure.common.RestResponse.userNotFoundExceptionResponse;

import com.example.eldorg.domain.repo.UserRepo;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private final UserRepo userRepo;

  public CustomUserDetailsService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  @Override
  public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo
        .findByUsername(username)
        .map(CustomUserDetails::new)
        .orElseThrow(userNotFoundExceptionResponse(username));
  }
}
