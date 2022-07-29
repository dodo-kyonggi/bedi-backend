package com.deadline826.bedi.service;

import com.deadline826.bedi.domain.RefreshToken;
import com.deadline826.bedi.domain.User;
import com.deadline826.bedi.domain.dto.TokenDto;
import com.deadline826.bedi.domain.dto.UserDto;
import com.deadline826.bedi.security.CustomAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.http.HttpServletRequest;

public interface UserService {

    void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter);

    void saveUser(UserDto dto);   //유저정보 저장

    TokenDto login(UserDto dto);

    void updateRefreshToken(String username, RefreshToken refreshToken);  //RefreshToken 업데이트

    TokenDto refresh(String refreshToken);  //RefreshToken 으로 AccessToken 받아올때 사용

    User getUserFromAccessToken();  //토큰에서 회원 객체 추출

    UserDetails loadUserById(Long id);
}
