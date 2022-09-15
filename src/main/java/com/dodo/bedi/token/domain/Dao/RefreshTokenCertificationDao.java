package com.dodo.bedi.token.domain.Dao;

import com.dodo.bedi.security.JwtConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class RefreshTokenCertificationDao {




    private final int LIMIT_TIME = 2 * 60;  // (2)

    private final StringRedisTemplate stringRedisTemplate;

    public void saveRefreshTokenCertification(String id, String token) { //(3)
        stringRedisTemplate.opsForValue()
                .set(id, token, Duration.ofMillis(JwtConstants.RT_EXP_TIME));


    }

    public String getRefreshTokenCertification(String id) { // (4)
        return stringRedisTemplate.opsForValue().get(id);
    }

    public void removeRefreshTokenCertification(String id) { // (5)
        stringRedisTemplate.delete(id);
    }

    public boolean hasKey(String id) {  //(6)
        return stringRedisTemplate.hasKey(id);
    }


}