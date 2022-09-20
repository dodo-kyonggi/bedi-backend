package com.dodo.bedi.token.service;

import com.dodo.bedi.token.domain.Dao.RefreshTokenCertificationDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RefreshTokenCertificationService {


    private final RefreshTokenCertificationDao refreshTokenCertificationDao;

    public void saveRefreshToken(String id, String refreshToken) {

        refreshTokenCertificationDao.saveRefreshTokenCertification(id,refreshToken);

    }

    public String findRefreshToken(String id) {

        return  refreshTokenCertificationDao.getRefreshTokenCertification(id);

    }
}