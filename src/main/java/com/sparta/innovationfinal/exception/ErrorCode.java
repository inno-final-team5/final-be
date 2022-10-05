package com.sparta.innovationfinal.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum ErrorCode {
    //로그인(토큰) 관련 오류
    NULL_TOKEN("NULL_TOKEN","토큰값 없음"),
    INVALID_TOKEN("INVALID_TOKEN","유효하지 않는 토큰"),
    EXPIRED_TOKEN("EXPIRED_TOKEN","토큰 만료"),
    MEMBER_NOT_FOUND("MEMBER_NOT_FOUND","사용자를 찾을 수 없음"),

    // 회원가입 관련 오류
    DUPLICATE_EMAIL("DUPLICATE_EMAIL","이메일 중복"),
    DUPLICATE_NICKNAME("DUPLICATE_NICKNAME","닉네임 중복"),
    DUPLICATE_LIKE("DUPLICATE_LIKE","이미 좋아요 누름"),

    //서비스 관련 오류
    INVALID_BADGEID("INVALID_BADGEID","배지가 없음"),
    INVALID_MOVIE("INVALID_MOVIE","해당 영화 없음"),
    INVALID_MEMBER("INVALID_MEMBER","해당 유저 없음"),
    INVALID_POST("INVALID_POST","해당 포스트 없음"),
    INVALID_REVIEW("INVALID_REVIEW","해당 한줄평 없음"),
    INVALID_CONTENT("INVALID_CONTENT","해당 내용 없음"),
    INVALID_STAR("INVALID_STAR","한줄평 별점 없음"),
    INVALID_TITLE("INVALID_TITLE","해당 제목 없음"),
    INVALID_CATEGORY("INVALID_CATEGORY","해당 카테고리 미설정"),
    INVALID_LIKE("INVALID_LIKE","해당 게시글에 해당 유저가 좋아요를 누르지 않음"),
    NON_FAVORITE_MOVIE("NON_FAVORITE_MOVIE","유저 즐겨찾기 목록에 해당 영화 없음"),
    NOT_AUTHOR("NOT_AUTHOR","작성자와 요청자가 다름"),
    DUPLICATE_FAVORITE_MOVIE("DUPLICATE_FAVORITE_MOVIE", "영화가 이미 즐겨찾기에 추가됨");

    private final String code;
    private final String message;
}
