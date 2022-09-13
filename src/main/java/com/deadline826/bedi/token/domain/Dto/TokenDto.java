package com.deadline826.bedi.token.domain.Dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Builder
@Getter
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpireTime;  //getExpiration 이 Date 타입
}
