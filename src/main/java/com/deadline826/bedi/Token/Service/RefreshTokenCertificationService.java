package com.deadline826.bedi.Token.Service;

import com.deadline826.bedi.SMS.Domain.Dao.SmsCertificationDao;
import com.deadline826.bedi.SMS.Domain.Dto.SmsCertificationRequest;
import com.deadline826.bedi.SMS.Template.SmsMessageTemplate;
import com.deadline826.bedi.Token.Domain.Dao.RefreshTokenCertificationDao;
import com.deadline826.bedi.exception.AuthenticationNumberMismatchException;
import com.deadline826.bedi.exception.SmsSendFailedException;
import lombok.RequiredArgsConstructor;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

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