package com.sparta.innovationfinal.dto.responseDto;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MovieResultResponseDto {

    private Long[] genre_ids;
    private Long id;
    private String poster_path;
    private String title;
    private Long movieId;

}
