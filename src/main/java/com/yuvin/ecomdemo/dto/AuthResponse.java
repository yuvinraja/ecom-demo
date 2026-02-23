package com.yuvin.ecomdemo.dto;

import com.yuvin.ecomdemo.entity.Role;

public class AuthResponse {

  private String token;
  private String type = "Bearer";
  private Long userId;
  private String name;
  private String email;
  private Role role;

  public AuthResponse() {
  }

  public AuthResponse(String token, Long userId, String name, String email, Role role) {
    this.token = token;
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.role = role;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }
}
