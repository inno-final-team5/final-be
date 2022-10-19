package com.sparta.innovationfinal.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubCommentRequestDto {
    private Long postId;
    private Long commentId;
    private String subCommentContent;
}
