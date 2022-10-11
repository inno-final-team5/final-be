package com.sparta.innovationfinal.badge;

import com.sparta.innovationfinal.dto.responseDto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BadgeController {
    private final BadgeService badgeService;
    private final BadgeRepository badgeRepository;

    @GetMapping(value = "/badge")
    public ResponseDto<?> getBadge() {
        return badgeService.getBadge();
    }

    @PostMapping(value = "/badge/add")
    public void add(@RequestBody BadgeResponseDto add) {
        Badge badge = new Badge(add);
        badgeRepository.save(badge);
    }
}
