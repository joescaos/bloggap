package com.joescaos.my_blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class ErrorDetails {

    private LocalDate timestamp;
    private String message;
    private String details;
}
