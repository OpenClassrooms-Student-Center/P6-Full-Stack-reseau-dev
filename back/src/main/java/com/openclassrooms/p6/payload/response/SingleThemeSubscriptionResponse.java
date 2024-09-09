package com.openclassrooms.p6.payload.response;

public record SingleThemeSubscriptionResponse(
        Long themeId,
        Boolean isSubscribed) {

}
