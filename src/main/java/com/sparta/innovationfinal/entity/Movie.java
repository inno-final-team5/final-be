package com.sparta.innovationfinal.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long movieId;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String posterPath;
    @Column(nullable = false)
    private int favoriteNum;

    public void update(int size) {
        this.favoriteNum = size;
    }

}
