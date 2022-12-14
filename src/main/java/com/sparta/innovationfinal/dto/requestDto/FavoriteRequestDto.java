package com.sparta.innovationfinal.dto.requestDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteRequestDto {

    private Long movieId;
    private String posterPath;
    private String title;

}
