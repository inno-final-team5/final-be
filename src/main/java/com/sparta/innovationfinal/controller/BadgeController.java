package com.sparta.innovationfinal.controller;

import com.sparta.innovationfinal.repository.BadgeRepository;
import com.sparta.innovationfinal.dto.responseDto.BadgeResponseDto;
import com.sparta.innovationfinal.service.BadgeService;
import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import com.sparta.innovationfinal.entity.Badge;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;
    private final BadgeRepository badgeRepository;

    @GetMapping(value = "/badge")
    public ResponseDto<?> getBadge() {
        return badgeService.getAllBadge();
    }

    @GetMapping(value = "/auth/badge")
    public ResponseDto<?> getMyBadge(HttpServletRequest request) {
        return badgeService.getMyBadge(request);
    }

    @PostMapping(value = "/auth/mainBadge/{badgeId}")
    public ResponseDto<?> addMainBadge(@PathVariable Long badgeId, HttpServletRequest request) {
        return badgeService.addMainBadge(badgeId, request);
    }

    @DeleteMapping(value = "/auth/mainBadge")
    public ResponseDto<?> deleteMainBadge(HttpServletRequest request) {
        return badgeService.cancelMainBadge(request);
    }

    @GetMapping(value = "/auth/mainBadge")
    public ResponseDto<?> getMainBadge(HttpServletRequest request) {
        return badgeService.getMainBadge(request);
    }

    @PostMapping(value = "/badge/add")
    public void add(@RequestBody BadgeResponseDto add) {
        Badge badge = new Badge(add);
        badgeRepository.save(badge);
    }
}
