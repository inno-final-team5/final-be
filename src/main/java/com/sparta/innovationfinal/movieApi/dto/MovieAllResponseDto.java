package com.sparta.innovationfinal.movieApi.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieAllResponseDto {

    private Long page;
    private List<MovieResultResponseDto> results = new ArrayList<>();
}
