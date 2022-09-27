package com.sparta.innovationfinal.movieApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailResponseDto {

    private List<?> genres;
    private Long id;
    private String overview;
    private String poster_path;
    private String title;
}
