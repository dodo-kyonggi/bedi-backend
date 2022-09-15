package com.dodo.bedi.sms.domain.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SmsCertificationRequest {

    private String phone;
    private String certificationNumber;

    @Builder
    public SmsCertificationRequest(String phone, String certificationNumber) {
        this.phone = phone;
        this.certificationNumber = certificationNumber;
    }

}