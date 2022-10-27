package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MovieResponseDto<T> {

    private Long statusCode;
    private String msg;
    private T data;
}
