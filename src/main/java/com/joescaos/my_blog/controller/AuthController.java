package com.joescaos.my_blog.controller;

import com.joescaos.my_blog.dto.JWTAuthResponse;
import com.joescaos.my_blog.dto.LoginDto;
import com.joescaos.my_blog.dto.RegisterDTO;
import com.joescaos.my_blog.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = {"/login", "/signin"})
  public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto) {
    String token = authService.login(loginDto);
    JWTAuthResponse tokenResponse = JWTAuthResponse.builder().accessToken(token).build();
    return ResponseEntity.ok().body(tokenResponse);
  }

  @PostMapping(value = {"/register", "/signup"})
  public ResponseEntity<String> register(@RequestBody RegisterDTO registerDTO) {
    return ResponseEntity.ok(authService.register(registerDTO));
  }
}
