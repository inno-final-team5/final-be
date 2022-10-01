package com.sparta.innovationfinal.movieApi;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MovieGenre {
        action(28, "action"), adventure(12, "adventure"), animation(16, "animation"),
        comedy(35, "comedy"), crime(80, "crime"), documentary(99, "documentary"), drama(18, "drama"),
        family(10751, "family"), fantasy(14, "fantasy"), history(36, "history"), horror(27, "horror"),
        music(10402, "music"), mystery(9648, "mystery"), romance(10749, "romance"),
        scienceFiction(878, "scienceFiction"), tvmovie(10770, "tvmovie"), thriller(53, "thriller"),
        war(10752, "war"), western(37, "western");

    private final int value;
    private final String GenreName;

}
