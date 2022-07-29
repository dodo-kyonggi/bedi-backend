package com.deadline826.bedi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.deadline826.bedi.domain.RefreshToken;
import com.deadline826.bedi.domain.User;

import com.deadline826.bedi.domain.dto.TokenDto;
import com.deadline826.bedi.domain.dto.UserDto;

import com.deadline826.bedi.exception.CustomAuthenticationException;
import com.deadline826.bedi.repository.RefreshTokenRepository;
import com.deadline826.bedi.repository.UserRepository;

import com.deadline826.bedi.security.CustomAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.deadline826.bedi.security.JwtConstants.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import io.jsonwebtoken.Jwts;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private CustomAuthenticationFilter authenticationFilter;

    @Override
    public void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    //토큰에서 회원 객체 추출
    public User getUserFromAccessToken() {
        try {
            // Authorization filter에서 SecurityContextHolder에서 set한 authentication 객체를 가져온다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getPrincipal().toString();
            return userRepository.findById(Long.parseLong(userId)).get();
        } catch (Exception e) {
            log.error(String.valueOf(e));
            return null;
        }
    }

    // 로그인 처리 후 JWT토큰 발급
    public TokenDto login(UserDto userDto) {
        try {
            //CustomAuthenticationFilter 의 attemptAuthentication 으로 이동
            //request 에는  id, password 정보가 들어있음
            Authentication authentication = authenticationFilter.attemptAuthentication(userDto);

            // springframework.security.core.userdetails
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authentication.getPrincipal();
            String id = user.getUsername();

            Object principal = authentication.getPrincipal();

            Optional<com.deadline826.bedi.domain.User> userId = userRepository.findById(Long.parseLong(id));
            String kakao_random_id = userId.get().getId().toString();

            //토큰 생성
            String accessToken = JWT.create()
                    .withSubject(kakao_random_id)  // 카카오가 넘겨주는 랜덤 값
                    .withExpiresAt(new Date(System.currentTimeMillis() + AT_EXP_TIME))  // 토큰 만료시간
                    .withClaim("username", userDto.getUsername())   //사용자 이름
                    .withIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 생성시간
                    .sign(Algorithm.HMAC256(JWT_SECRET));  //JWT_SECRET 키로 암호화
            String refreshToken = JWT.create()
                    .withSubject(kakao_random_id)
                    .withExpiresAt(new Date(System.currentTimeMillis() + RT_EXP_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(JWT_SECRET));

            // Refresh Token DB에 저장

            RefreshToken remainRefreshToken = userId.get().getRefreshToken();
            if (remainRefreshToken!=null){
                remainRefreshToken.setToken(refreshToken);
            } else{
                RefreshToken refreshToken1 = new RefreshToken();
                refreshToken1.setToken(refreshToken);
                RefreshToken save = refreshTokenRepository.save(refreshToken1);

                //RefreshToken 의 기본키를 user 의 외래키로 설정
                updateRefreshToken(kakao_random_id, save);
            }

            return TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();
        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException("로그인 잘못됨");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //유저 정보 가져오기
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("UserDetailsService - loadUserByUsername : 사용자를 찾을 수 없습니다."));

        // authorities 대신 Collections.EMPTY_LIST을 넣어 Role 없이 인증가능하게 했다
        //CustomAuthProvider 의 authenticate 로 복귀
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),Collections.EMPTY_LIST);
    }

    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {

        //유저 정보 가져오기
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("UserDetailsService - loadUserByUsername : 사용자를 찾을 수 없습니다."));

        // authorities 대신 Collections.EMPTY_LIST을 넣어 Role 없이 인증가능하게 했다
        //CustomAuthProvider 의 authenticate 로 복귀
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(),Collections.EMPTY_LIST);
    }

    @Override
    public void saveUser(UserDto dto) {
        boolean isSave = validateDuplicateUserId(dto);  //검증하고
        if (!isSave) {
            dto.encodePassword(passwordEncoder.encode(dto.getPassword()));  //비밀번호 암호화 해서
            userRepository.save(dto.toEntity());   //저장하기
        }
    }

    private boolean validateDuplicateUserId(UserDto dto) {
        if (userRepository.existsById(dto.getId())) {     // findByUsername -> findById  이름말고 아이디로 검사하게끔 수정
            return true;
        }
        return false;
    }

    // =============== TOKEN ============ //

    @Override
    public void updateRefreshToken(String username, RefreshToken refreshToken) {
        User user = userRepository.findById((Long.parseLong( username))).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        user.updateRefreshToken(refreshToken);
    }

    @Override
    public TokenDto refresh(String refreshToken) {

        // === Refresh Token 유효성 검사 === //
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        // === Access Token 재발급 === //
        long now = System.currentTimeMillis();
        String id = decodedJWT.getSubject();

        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!user.getRefreshToken().getToken().equals(refreshToken)) {
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(now + AT_EXP_TIME))
                .sign(Algorithm.HMAC256(JWT_SECRET));

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        if (diffMin < 5) {
            String newRefreshToken = JWT.create()
                    .withSubject(user.getId().toString())  // 카카오가 넘겨주는 랜덤 값
                    .withExpiresAt(new Date(now + RT_EXP_TIME))
                    .sign(Algorithm.HMAC256(JWT_SECRET));
            user.getRefreshToken().setToken(newRefreshToken);
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
