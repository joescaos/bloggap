package com.joescaos.my_blog.service;

import com.joescaos.my_blog.dto.LoginDto;
import com.joescaos.my_blog.dto.RegisterDTO;

public interface AuthService {
  String login(LoginDto loginDto);

  String register(RegisterDTO registerDTO);
}
