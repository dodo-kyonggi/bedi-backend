package com.dodo.bedi.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtConstants {

    // Expiration Time
    public static final long MINUTE = 1000 * 60;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;

    public static final long AT_EXP_TIME =  3 * HOUR;
    public static final long RT_EXP_TIME =  2 * MONTH;

    // Secret
    public static final String JWT_SECRET = "kakao_test_key";

    // Header
    public static final String AT_HEADER = "access_token";
    public static final String RT_HEADER = "refresh_token";
    public static final String TOKEN_HEADER_PREFIX = "Bearer ";
}
