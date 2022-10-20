package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
    private Long commentId;
    private String commentContent;
    private String nickname;
    private Long badgeId;
    List<SubCommentResponseDto> subCommentResponseDtoList;
    private String createdAt;
    private String modifiedAt;
}
