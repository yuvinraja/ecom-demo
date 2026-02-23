package com.yuvin.ecomdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.yuvin.ecomdemo.dto.AuthResponse;
import com.yuvin.ecomdemo.dto.LoginRequest;
import com.yuvin.ecomdemo.dto.RegisterRequest;
import com.yuvin.ecomdemo.dto.UserDto;
import com.yuvin.ecomdemo.entity.Role;
import com.yuvin.ecomdemo.entity.User;
import com.yuvin.ecomdemo.exception.ResourceNotFoundException;
import com.yuvin.ecomdemo.exception.UserAlreadyExistsException;
import com.yuvin.ecomdemo.repository.UserRepository;
import com.yuvin.ecomdemo.security.JwtService;

@Service
public class AuthService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  public AuthResponse register(RegisterRequest request) {
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new UserAlreadyExistsException("User with email " + request.getEmail() + " already exists");
    }

    User user = new User();
    user.setName(request.getName());
    user.setEmail(request.getEmail());
    user.setPassword(passwordEncoder.encode(request.getPassword()));
    user.setRole(Role.USER);

    userRepository.save(user);

    String token = jwtService.generateToken(user);

    return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
  }

  public AuthResponse login(LoginRequest request) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("Invalid email or password");
    }

    User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    String token = jwtService.generateToken(user);

    return new AuthResponse(token, user.getId(), user.getName(), user.getEmail(), user.getRole());
  }

  public UserDto getCurrentUser(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    return new UserDto(user.getId(), user.getName(), user.getEmail(), user.getRole().name());
  }

  public User getUserByEmail(String email) {
    return userRepository.findByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
  }
}
