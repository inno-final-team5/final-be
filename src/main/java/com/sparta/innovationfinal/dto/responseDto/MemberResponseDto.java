package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    
    private Long memberId;
    private String email;
    private String nickname;
    //배지도 나중에 추가해야함
    private String createdAt;
    private String modifiedAt;
    
}
