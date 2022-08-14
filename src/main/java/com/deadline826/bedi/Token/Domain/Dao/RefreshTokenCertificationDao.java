package com.deadline826.bedi.Token.Domain.Dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

import static com.deadline826.bedi.security.JwtConstants.RT_EXP_TIME;

@RequiredArgsConstructor
@Repository
public class RefreshTokenCertificationDao {




    private final int LIMIT_TIME = 2 * 60;  // (2)

    private final StringRedisTemplate stringRedisTemplate;

    public void saveRefreshTokenCertification(Long id, String token) { //(3)
        stringRedisTemplate.opsForValue()
                .set(String.valueOf(id), token, Duration.ofMillis(RT_EXP_TIME));

    }

    public String getRefreshTokenCertification(Long id) { // (4)
        return stringRedisTemplate.opsForValue().get(id);
    }

    public void removeRefreshTokenCertification(Long id) { // (5)
        stringRedisTemplate.delete(String.valueOf(id));
    }

    public boolean hasKey(Long id) {  //(6)
        return stringRedisTemplate.hasKey(String.valueOf(id));
    }
}