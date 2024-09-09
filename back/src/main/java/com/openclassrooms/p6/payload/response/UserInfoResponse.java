package com.openclassrooms.p6.payload.response;

import java.time.LocalDateTime;

/**
 * Response payload for retrieving user information.
 */
public record UserInfoResponse(Long id, String username, String email, LocalDateTime created_at,
        LocalDateTime updated_at) {
}