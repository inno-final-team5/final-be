package com.sparta.innovationfinal.movieApi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponseDto<T> {

    private Long statusCode;
    private String msg;
    private T data;
}
