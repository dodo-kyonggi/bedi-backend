package com.deadline826.bedi.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpired;
}
