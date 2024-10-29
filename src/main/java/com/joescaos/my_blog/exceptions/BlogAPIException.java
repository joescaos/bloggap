package com.joescaos.my_blog.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class BlogAPIException extends RuntimeException {

    private HttpStatus httpStatus;
    private String message;

    public BlogAPIException(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
