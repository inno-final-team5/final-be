package com.sparta.innovationfinal.dto.responseDto;

import com.sparta.innovationfinal.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
    private boolean isSuccess;
    private T data;
    private ErrorCode error;

    public static <T> ResponseDto<T> success(T data) {
        return new ResponseDto<>(true, data, null);
    }
 

    public static <T> ResponseDto<T> fail(ErrorCode error) {
        return new ResponseDto<>(false, null, error);
    }
}
