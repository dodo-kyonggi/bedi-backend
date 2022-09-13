package com.deadline826.bedi.token.service;

import com.deadline826.bedi.token.domain.Dao.RefreshTokenCertificationDao;
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

    public void removeRefreshToken(String id) {

        refreshTokenCertificationDao.removeRefreshTokenCertification(id);

    }

    public boolean hasRefreshToken(String id) {

        return refreshTokenCertificationDao.hasKey(id);

    }



}