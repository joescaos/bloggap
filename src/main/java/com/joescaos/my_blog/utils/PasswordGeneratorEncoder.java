package com.joescaos.my_blog.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGeneratorEncoder {
  public static void main(String[] args) {
    PasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.encode("luis"));
    System.out.println(encoder.encode("admin"));
  }
}
