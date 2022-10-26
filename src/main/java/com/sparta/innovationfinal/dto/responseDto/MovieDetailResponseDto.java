package com.sparta.innovationfinal.dto.responseDto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieDetailResponseDto {

    private List<Object> genres;
    private Long id;
    private String overview;
    private String poster_path;
    private String title;
    private String release_date;
    private boolean favoriteCheck;
}
