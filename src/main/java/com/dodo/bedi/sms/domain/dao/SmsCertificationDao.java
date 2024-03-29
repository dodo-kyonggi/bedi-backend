package com.dodo.bedi.sms.domain.dao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsCertificationDao {

    private final String PREFIX = "sms:";  // (1)
    private final int LIMIT_TIME = 2 * 60;  // (2)

    private final StringRedisTemplate stringRedisTemplate;

    //레디스에 인증번호 생성
    public void createSmsCertification(String phone, String certificationNumber) { //(3)
        stringRedisTemplate.opsForValue()
                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));

    }

    public String getSmsCertification(String phone) { // (4)
        return stringRedisTemplate.opsForValue().get(PREFIX + phone);
    }

    public void removeSmsCertification(String phone) { // (5)
        stringRedisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {  //(6)
        return stringRedisTemplate.hasKey(PREFIX + phone);
    }
}