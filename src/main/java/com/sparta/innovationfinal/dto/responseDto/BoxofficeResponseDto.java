package com.sparta.innovationfinal.dto.responseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BoxofficeResponseDto {

    private int ranking;
    private Long movieId;
    private String title;
    private String tag;
    private String poster_path;
}
