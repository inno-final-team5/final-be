//package com.sparta.innovationfinal.api.Kobis.domain;
//
//import lombok.Data;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//public class BoxOfficeWeekly {
//    @javax.persistence.Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "BoxOffice_movie_id")
//    private Long Id;
//    //박스오피스 순위 --추가
//    private String ranking;
//    //영화제목
//    private String title;
//    //영화장르
//    private String tag;
//    //영화포스터
//    private String imageUrl;
//    //DB저장일자 --추가
//    private  String targetDt;
//
//    @CreationTimestamp
//    private LocalDateTime modification_date_time;
//}
