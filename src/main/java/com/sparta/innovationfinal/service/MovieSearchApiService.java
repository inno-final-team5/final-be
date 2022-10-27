package com.sparta.innovationfinal.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sparta.innovationfinal.dto.MovieGenre;
import com.sparta.innovationfinal.dto.responseDto.MovieAllResponseDto;
import com.sparta.innovationfinal.dto.responseDto.MovieDetailResponseDto;
import com.sparta.innovationfinal.dto.responseDto.MovieGenreResponseDto;
import com.sparta.innovationfinal.dto.responseDto.MovieTitleSearchResponseDto;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Service
public class MovieSearchApiService {

    private static final String api_key = "945d4a8051fd9cafc9c1a4bc1e8fcc36";

    // 전체 조회
    public MovieAllResponseDto movieAllSearch(int pageNum) throws Exception {

        HttpHeaders httpHeaders = makeHeaders();
        //Object Mapper를 통한 JSON 바인딩
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
            ResponseEntity responseEntity = restTemplate.exchange(
                    "https://api.themoviedb.org/3/movie/popular?api_key=" + api_key + "&language=ko&page=" + pageNum + "&region=KR",
                    HttpMethod.GET, httpEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());

        Gson gson = new Gson();
        MovieAllResponseDto movieAllResponseDto = gson.fromJson(jsonObject.toJSONString(), MovieAllResponseDto.class);

        return movieAllResponseDto;
    }

    // 상세 조회
    public MovieDetailResponseDto movieDetailSearch(int movieId) throws Exception{
        HttpHeaders httpHeaders = makeHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity responseEntity = restTemplate.exchange(
                "https://api.themoviedb.org/3/movie/"+movieId+"?api_key="+api_key+
                        "&language=ko", HttpMethod.GET, httpEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());

        Gson gson = new Gson();
        MovieDetailResponseDto movieDetailResponseDto = gson.fromJson(jsonObject.toString(), MovieDetailResponseDto.class);

        return movieDetailResponseDto;
    }

    // 장르 조회
    public MovieGenreResponseDto MovieGenreSearch(MovieGenre genre, int pageNum) throws Exception{
        HttpHeaders httpHeaders = makeHeaders();
        String genreStringId = Integer.toString(genre.getValue());

        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity responseEntity = restTemplate.exchange(
                "https://api.themoviedb.org/3/discover/movie?api_key="+api_key+
                        "&language=ko-KR&sort_by=popularity.desc&include_adult=false&include_video=false&page="+ pageNum +
                        "&primary_release_date.gte=2000-01-01&primary_release_date.lte=2100-12-31&with_genres="+genreStringId+"\n",
                HttpMethod.GET, httpEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());

        Gson gson = new Gson();
        MovieGenreResponseDto movieGenreResponseDto = gson.fromJson(jsonObject.toString(), MovieGenreResponseDto.class);

        return movieGenreResponseDto;
    }

    // 제목으로 검색하기
    public MovieTitleSearchResponseDto MovieTitleSearch(String movieTitle, int pageNum) throws Exception{
        HttpHeaders httpHeaders = makeHeaders();
        HttpEntity httpEntity = new HttpEntity(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity responseEntity = restTemplate.exchange(
                "https://api.themoviedb.org/3/search/movie?api_key="+api_key
                        +"&language=ko&query="+movieTitle+"&page="+pageNum+"&include_adult=false&region=KR",
                HttpMethod.GET, httpEntity, String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody().toString());

        Gson gson = new Gson();
        MovieTitleSearchResponseDto movieTitleSearchResponseDto = gson.fromJson(jsonObject.toString(), MovieTitleSearchResponseDto.class);

        return movieTitleSearchResponseDto;
    }

    private static HttpHeaders makeHeaders() {
        // SSL 버전 맞지 않는 것 때문에 추가
        System.setProperty("https.protocols", "TLSv1.2,TLSv1.1,SSLv3");
        //헤더 설정
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
        return httpHeaders;
    }

}
