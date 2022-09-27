package com.sparta.innovationfinal.movieApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResultResponseDto {

    private Long[] genre_ids;
    private Long id;
    private String poster_path;
    private String title;
}
