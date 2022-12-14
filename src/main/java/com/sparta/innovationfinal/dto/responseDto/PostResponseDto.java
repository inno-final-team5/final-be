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
public class PostResponseDto {
    private Long postId;
    private String nickname;
    private Long badgeId;
    private String postTitle;
    private String postCategory;
    private String postContent;
    private int LikeNum;
    private List<CommentResponseDto> commentResponseDtoList;
    private String createdAt;
    private String modifiedAt;
}
