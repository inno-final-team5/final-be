package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentResponseDto {
    private Long subCommentId;
    private String subCommentContent;
    private String nickname;
    private Long badgeId;
    private String createdAt;
    private String modifiedAt;
}
