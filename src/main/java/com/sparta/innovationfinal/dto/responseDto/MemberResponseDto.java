package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDto {
    
    private Long memberId;
    private String email;
    private String nickname;
    private Long badgeId;
    private String createdAt;
    private String modifiedAt;
    
}
