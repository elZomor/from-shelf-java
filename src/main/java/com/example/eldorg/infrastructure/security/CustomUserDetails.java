package com.example.eldorg.infrastructure.security;

import com.example.eldorg.domain.model.User;
import com.example.eldorg.domain.model.UserStatus;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {
  private final User user;

  public CustomUserDetails(User user) {
    this.user = user;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getUsername();
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserStatus.ACTIVE.name().equals(user.getStatus());
  }

  @Override
  public boolean isAccountNonLocked() {
    return UserStatus.ACTIVE.name().equals(user.getStatus());
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserStatus.ACTIVE.name().equals(user.getStatus());
  }

  @Override
  public boolean isEnabled() {
    return UserStatus.ACTIVE.name().equals(user.getStatus());
  }

  public User getUser() {
    return user;
  }
}
