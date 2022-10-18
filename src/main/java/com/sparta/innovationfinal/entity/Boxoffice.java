package com.sparta.innovationfinal.entity;

import com.sparta.innovationfinal.dto.responseDto.BoxofficeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Boxoffice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(nullable = false)
    private int ranking;

    @Column(nullable = false)
    private Long movieId;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String tag;

    @Column(nullable = false)
    private String poster_path;

    public Boxoffice(BoxofficeResponseDto add) {
        this.ranking = add.getRanking();
        this.movieId = add.getMovieId();
        this.title = add.getTitle();
        this.tag = add.getTag();
        this.poster_path = add.getPoster_path();

    }
}