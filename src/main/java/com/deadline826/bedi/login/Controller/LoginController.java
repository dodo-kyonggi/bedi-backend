package com.deadline826.bedi.login.Controller;

import com.deadline826.bedi.SMS.Domain.Dto.PhoneNumberDto;
import com.deadline826.bedi.SMS.Domain.Dto.SmsCertificationRequest;
import com.deadline826.bedi.SMS.Service.SmsCertificationService;
import com.deadline826.bedi.Token.Domain.Dto.TokenDto;
import com.deadline826.bedi.exception.AuthenticationNumberMismatchException;
import com.deadline826.bedi.exception.DuplicateEmailException;
import com.deadline826.bedi.login.Domain.Dto.LoginDto;
import com.deadline826.bedi.login.Domain.Dto.UserDto;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.login.Service.UserService;
import com.deadline826.bedi.login.Service.VerifyPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Random;

import static com.deadline826.bedi.security.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class LoginController {


    private final UserService userService;
    private final VerifyPasswordService verifyPasswordService;
    Random random = new Random();



    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) throws DuplicateEmailException {


        while (true){  //고유 아이디 생성
            userDto.setId(Long.parseLong(String.valueOf(1000000000 + random.nextInt(900000000))));
            User user = userService.findUserById(userDto.getId());
            if (user==null)
                break;
        }

        //유저정보 저장
        userService.saveUser(userDto);


        return ResponseEntity.ok().body("회원가입 완료");
    }


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) throws AuthenticationNumberMismatchException {

        // redis 를 아용하여 로그인 정보를 1분간 임시저장한다 (PW 검사시 이용됨)
        verifyPasswordService.makeTempPasswordStorage(loginDto.getEmail(),loginDto.getPassword());

        // loginDto 로 로그인 진행
        TokenDto loginToken = userService.login(loginDto);


        // 토큰정보를 반환한다
        return ResponseEntity.ok().body(loginToken);
    }



    //refreshToken 을 이용하여 accessToken 가져오기
    @GetMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_HEADER_PREFIX)) {
            throw new RuntimeException("JWT Token이 존재하지 않습니다.");
        }
        String refreshToken = authorizationHeader.substring(TOKEN_HEADER_PREFIX.length());
        TokenDto tokens = userService.refresh(refreshToken);  //refreshToken 을 넣으면 accessToken 이 반환 됨
        response.setHeader(AT_HEADER, tokens.getAccessToken());   //위의 accessToken 을 헤더에 넣고
        if (tokens.getRefreshToken() != null) {              // refreshToken 의 만료기간이 다가와서 새로 받은 refreshToken 이 있다면
            response.setHeader(RT_HEADER, tokens.getRefreshToken());  // refreshToken 도 같이 헤더에 넣는다.
        }
        return ResponseEntity.ok(tokens);   //화면에 출력
    }

    // 내정보 가져오기
    @GetMapping("/my")
    public ResponseEntity<Long> my(HttpServletRequest request) {
        User user = userService.getUserFromAccessToken();
        return ResponseEntity.ok(user.getId());
    }
}
