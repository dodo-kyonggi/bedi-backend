package com.dodo.bedi.sms.controller;

import com.dodo.bedi.sms.domain.dto.PhoneNumberDto;
import com.dodo.bedi.sms.domain.dto.SmsCertificationRequest;
import com.dodo.bedi.sms.service.SmsCertificationService;
import com.dodo.bedi.sms.exception.AuthenticationNumberMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class SmsController {


    private final SmsCertificationService smsCertificationService;


    //인증번호 발송
    @PostMapping("/send")
    public ResponseEntity<String> sendSms(@RequestBody PhoneNumberDto phoneNumberDto) {
        smsCertificationService.sendSms(phoneNumberDto.getPhone());
        return ResponseEntity.ok().body("인증번호 발송");
    }

    //인증번호 확인
    @PostMapping("/confirm")
    public ResponseEntity<String> SmsVerification(@RequestBody SmsCertificationRequest requestDto) throws AuthenticationNumberMismatchException {
        smsCertificationService.verifySms(requestDto);
        return ResponseEntity.ok().body("인증 완료");
    }
}
