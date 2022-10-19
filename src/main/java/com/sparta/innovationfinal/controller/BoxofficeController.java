package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.dto.responseDto.BoxofficeResponseDto;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Boxoffice;
import com.sparta.innovationfinal.repository.BoxofficeRepository;
import com.sparta.innovationfinal.service.BoxofficeService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Api(tags = {"박스오피스 get API"})
public class BoxofficeController {
    private final BoxofficeService boxofficeService;
    private final BoxofficeRepository boxofficeRepository;

    @GetMapping(value = "/main/boxoffice")
    public ResponseDto<?> getBoxoffice() {
        return boxofficeService.getBoxoffice();
    }

    @PostMapping(value = "/add")
    public void add(@RequestBody BoxofficeResponseDto add) {
        Boxoffice boxoffice = new Boxoffice(add);
        boxofficeRepository.save(boxoffice);
    }

}
