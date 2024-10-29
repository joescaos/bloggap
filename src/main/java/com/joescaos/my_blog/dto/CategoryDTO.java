package com.joescaos.my_blog.dto;

import com.joescaos.my_blog.entity.Post;
import java.util.List;
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
public class CategoryDTO {
    
    private Long id;
    private String name;
    private String description;
    private List<Post> posts;
}
