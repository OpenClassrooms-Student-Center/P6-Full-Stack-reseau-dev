package com.openclassrooms.p6.payload.request;

public record UserRequest(
        String username,
        String email) {
}
