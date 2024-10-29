package com.joescaos.my_blog.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
public class CommentDTO {

  private Long id;

  @NotEmpty(message = "name should not be empty")
  private String name;

  @NotEmpty(message = "email should not be empty")
  @Email
  private String email;

  @NotEmpty
  @Size(min = 10, message = "comment should have at least 10 characters")
  private String body;
}
