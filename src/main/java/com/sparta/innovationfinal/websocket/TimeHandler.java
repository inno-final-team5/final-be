package com.sparta.innovationfinal.websocket;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class TimeHandler {

    public static String setModifiedAtList(LocalDateTime modifiedAt) {

        String date = "";

        if(modifiedAt.getDayOfMonth() == LocalDateTime.now().getDayOfMonth() && modifiedAt.getMonthValue() == LocalDateTime.now().getMonthValue()){
            date = modifiedAt.format(DateTimeFormatter.ofPattern("HH:mm"));
        }else{
            date = modifiedAt.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        }

        return date;
    }

    public static String setModifiedAtComment(LocalDateTime modifiedAt){

        String date = "";

        if(modifiedAt.getDayOfMonth() == LocalDateTime.now().getDayOfMonth() && modifiedAt.getMonthValue() == LocalDateTime.now().getMonthValue()){
            long hour = ChronoUnit.HOURS.between(modifiedAt, LocalDateTime.now());
            date = String.valueOf(hour)+ "시간 전";
            if(hour < 1){
                date = String.valueOf(ChronoUnit.MINUTES.between(modifiedAt, LocalDateTime.now()))+"분 전";
            }
        }else{
            date = modifiedAt.format(DateTimeFormatter.ofPattern("yy.MM.dd"));
        }
        return date;
    }

//    public static String setModifiedAtAnswerDetail(LocalDateTime modifiedAt){
//        return modifiedAt.format(DateTimeFormatter.ofPattern("yy.MM.dd HH:mm"));
//    }
//
//    public static String setModifiedAtAnswerRecentSearch(LocalDateTime modifiedAt){
//        return modifiedAt.format(DateTimeFormatter.ofPattern("MM.dd"));
//    }


}
