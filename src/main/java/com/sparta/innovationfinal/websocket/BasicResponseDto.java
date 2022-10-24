package com.sparta.innovationfinal.websocket;

import lombok.Getter;

@Getter
public class BasicResponseDto {
    public BasicResponseDto(String result) {
        this.result = result;
    }

    private  String result;
}
