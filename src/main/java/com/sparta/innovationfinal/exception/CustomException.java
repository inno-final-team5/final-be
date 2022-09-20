package com.sparta.innovationfinal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException {
    private final ErrorCode errorCode;
}
