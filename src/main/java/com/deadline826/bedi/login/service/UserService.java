package com.deadline826.bedi.login.service;

import com.deadline826.bedi.login.exception.DuplicateEmailException;
import com.deadline826.bedi.login.domain.Dto.LoginDto;
import com.deadline826.bedi.login.domain.User;
import com.deadline826.bedi.token.domain.Dto.TokenDto;
import com.deadline826.bedi.login.domain.Dto.UserDto;
import com.deadline826.bedi.security.CustomAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {

    void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter); //의존성 주입

    void saveUser(UserDto dto) throws DuplicateEmailException;   //유저정보 저장

    TokenDto login(LoginDto loginDto);

//    void updateRefreshToken(String id, RefreshToken refreshToken);  //RefreshToken 업데이트

    TokenDto refresh(String refreshToken);  //RefreshToken 으로 AccessToken 받아올때 사용

    UserDetails loadUserById(Long id);

    User findUserById(Long id);

    User findUserByEmail(String email);

    UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;

    User getUserFromAccessToken();  //토큰에서 회원 객체 추출

}
