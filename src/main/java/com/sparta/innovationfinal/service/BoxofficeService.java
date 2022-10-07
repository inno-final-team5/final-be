package com.sparta.innovationfinal.service;

import com.sparta.innovationfinal.dto.responseDto.BoxofficeResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Boxoffice;
import com.sparta.innovationfinal.repository.BoxofficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoxofficeService {

    private final BoxofficeRepository boxofficeRepository;

    @Transactional
    public ResponseDto<?> getBoxoffice() {
        List<Boxoffice> boxofficeList = boxofficeRepository.findAll();
        List<BoxofficeResponseDto> boxofficeResponseDtoList = new ArrayList<>();
        for (Boxoffice boxoffice : boxofficeList) {
            boxofficeResponseDtoList.add(
                    BoxofficeResponseDto.builder()
                            .ranking(boxoffice.getRanking())
                            .movieId(boxoffice.getMovieId())
                            .title(boxoffice.getTitle())
                            .tag(boxoffice.getTag())
                            .poster_path(boxoffice.getPoster_path())
                            .build()
            );
        }
        return ResponseDto.success(boxofficeResponseDtoList);
    }

}
