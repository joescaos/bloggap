package com.joescaos.my_blog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JWTAuthResponse {

  private String accessToken;

  @Builder.Default
  private String tokenType = "Bearer";
}
