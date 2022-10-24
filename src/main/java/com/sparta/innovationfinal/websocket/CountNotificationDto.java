package com.sparta.innovationfinal.websocket;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountNotificationDto {

    private Long notificationCount;

    public CountNotificationDto(Long notificationCount) {
        this.notificationCount = notificationCount;
    }
}
