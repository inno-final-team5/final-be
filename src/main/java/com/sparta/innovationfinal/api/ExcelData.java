package com.sparta.innovationfinal.api;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ExcelData {

        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long Id;
        @Column(nullable = false)
        private int ranking;
        @Column(nullable = false)
        private int movieId;
        @Column(nullable = false)
        private String title;
        @Column(nullable = false)
        private String tag;
        @Column(nullable = false)
        private String poster_path;

}

