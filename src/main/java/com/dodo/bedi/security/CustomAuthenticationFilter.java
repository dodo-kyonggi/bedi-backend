package com.dodo.bedi.security;

import com.dodo.bedi.login.domain.Dto.LoginDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
public class CustomAuthenticationFilter {
    //SecurityConfig 의 authenticationManagerBean 에서 주입받는다
    private  AuthenticationManager authenticationManager;


    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public Authentication attemptAuthentication(LoginDto loginDto) throws AuthenticationException {
        try {

            String email = loginDto.getEmail();
            String password = loginDto.getPassword();

            //email, password 로 토큰생성
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);


            //CustomAuthProvider 의 authenticate 에서 토큰을 검사
            Authentication authenticate = authenticationManager.authenticate(token);


            // UserServiceImpl 의 login 으로 값 리턴
            return authenticate;

        } catch (Exception e) {
            log.error(String.valueOf(e));
            return null;
        }
    }



}
