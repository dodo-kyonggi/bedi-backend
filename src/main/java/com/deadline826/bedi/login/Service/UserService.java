package com.deadline826.bedi.login.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Token.Domain.RefreshToken;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.Token.Domain.Dto.TokenDto;
import com.deadline826.bedi.login.Domain.Dto.UserDto;
import com.deadline826.bedi.security.CustomAuthenticationFilter;
import org.springframework.security.core.userdetails.UserDetails;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

public interface UserService {



    User getUser(String id); //유저정보 불러오기

    void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter);

    void saveUser(UserDto dto);   //유저정보 저장

    TokenDto login(UserDto dto);

    void updateRefreshToken(String username, RefreshToken refreshToken);  //RefreshToken 업데이트

    TokenDto refresh(String refreshToken);  //RefreshToken 으로 AccessToken 받아올때 사용

    String resolveToken(HttpServletRequest request);   // Request의 Header에서 token 값을 가져옵니다.

    String getUserId(String token);  //토큰에서 회원 아이디 추출

    UserDetails loadUserById(Long id);

    User getUserFromAccessToken();  //토큰에서 회원 객체 추출
}
