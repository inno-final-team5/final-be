package com.sparta.innovationfinal.dto.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieFavoriteRankingDto {

    private int ranking;
    private Long id;
    private String poster_path;
    private String title;
    private Long movieId;
    private int favoriteNum;
}
