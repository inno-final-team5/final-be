package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AllPostResponseDto {
    private Long postId;
    private String nickname;
    private Long badgeId;
    private String postTitle;
    private String postCategory;
    private String createdAt;
}
