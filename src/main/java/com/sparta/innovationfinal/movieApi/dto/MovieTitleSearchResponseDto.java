package com.sparta.innovationfinal.movieApi.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieTitleSearchResponseDto {

    private Long page;
    private List<MovieResultResponseDto> results = new ArrayList<>();
}
