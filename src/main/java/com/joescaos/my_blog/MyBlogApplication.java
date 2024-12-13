package com.joescaos.my_blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Spring Boot Blog Application",
            description = "Blog application developed with Spring Boot",
            version = "v1.0"),
    externalDocs =
        @ExternalDocumentation(
            description = "Spring Boot Blog App Documentation",
            url = "https://github.com/joescaos/bloggap"))
public class MyBlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyBlogApplication.class, args);
  }
}
