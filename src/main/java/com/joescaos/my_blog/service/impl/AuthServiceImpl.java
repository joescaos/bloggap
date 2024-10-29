package com.joescaos.my_blog.service.impl;

import com.joescaos.my_blog.dto.LoginDto;
import com.joescaos.my_blog.dto.RegisterDTO;
import com.joescaos.my_blog.entity.Role;
import com.joescaos.my_blog.entity.User;
import com.joescaos.my_blog.exceptions.BlogAPIException;
import com.joescaos.my_blog.repository.RoleRepository;
import com.joescaos.my_blog.repository.UserRepository;
import com.joescaos.my_blog.security.JwtTokenProvider;
import com.joescaos.my_blog.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;

  private final UserRepository userRepository;

  private final RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  private final JwtTokenProvider tokenProvider;

  public AuthServiceImpl(
      AuthenticationManager authenticationManager,
      UserRepository userRepository,
      RoleRepository roleRepository,
      PasswordEncoder passwordEncoder,
      JwtTokenProvider tokenProvider) {
    this.authenticationManager = authenticationManager;
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenProvider = tokenProvider;
  }

  @Override
  public String login(LoginDto loginDto) {

    Authentication authenticate =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authenticate);

    return tokenProvider.generateToken(authenticate);
  }

  @Override
  public String register(RegisterDTO registerDTO) {

    if (userRepository.existsByUsername(registerDTO.getUsername())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Username already exists");
    }

    if (userRepository.existsByEmail(registerDTO.getEmail())) {
      throw new BlogAPIException(HttpStatus.BAD_REQUEST, "email already exists");
    }

    User user =
        User.builder()
            .name(registerDTO.getName())
            .username(registerDTO.getUsername())
            .email(registerDTO.getEmail())
            .password(passwordEncoder.encode(registerDTO.getPassword()))
            .build();

    Set<Role> roles = new HashSet<>();
    Role userRole = roleRepository.findByName("ROLE_USER").get();
    roles.add(userRole);

    user.setRoles(roles);

    userRepository.save(user);
    return "User registered successfully";
  }
}
