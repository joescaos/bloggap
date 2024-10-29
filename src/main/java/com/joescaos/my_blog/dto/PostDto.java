package com.joescaos.my_blog.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    @NotEmpty
    @Size(min = 2, message = "Post title should have at least two letters")
    private String title;
    @NotEmpty
    @Size(min = 10, message = "Post description should have at least ten letters")
    private String description;
    @NotEmpty
    private String content;
    private Set<CommentDTO> comments;
    private Long categoryId;
}
