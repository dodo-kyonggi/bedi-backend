package com.dodo.bedi.login.service;

import com.dodo.bedi.login.exception.DuplicateEmailException;
import com.dodo.bedi.login.domain.Dto.LoginDto;
import com.dodo.bedi.login.domain.User;
import com.dodo.bedi.token.domain.Dto.TokenDto;
import com.dodo.bedi.login.domain.Dto.UserDto;
import com.dodo.bedi.security.CustomAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter); //의존성 주입

    void saveUser(UserDto dto) throws DuplicateEmailException;   //유저정보 저장

    TokenDto login(LoginDto loginDto);


    TokenDto refresh(String refreshToken);  //RefreshToken 으로 AccessToken 받아올때 사용

    //UserDetails loadUserById(Long id);

    User findUserById(Long id);

    //User findUserByEmail(String email);

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    User getUserFromAccessToken();  //토큰에서 회원 객체 추출

}
