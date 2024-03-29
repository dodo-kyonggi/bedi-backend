package com.dodo.bedi.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
//import com.deadline826.bedi.Token.Domain.RefreshToken;
import com.dodo.bedi.token.service.RefreshTokenCertificationService;
import com.dodo.bedi.login.exception.DuplicateEmailException;
import com.dodo.bedi.login.domain.Dto.LoginDto;
import com.dodo.bedi.login.domain.User;

import com.dodo.bedi.token.domain.Dto.TokenDto;
import com.dodo.bedi.login.domain.Dto.UserDto;

import com.dodo.bedi.login.exception.CustomAuthenticationException;
import com.dodo.bedi.goal.repository.GoalRepository;
//import com.deadline826.bedi.Token.repository.RefreshTokenRepository;
import com.dodo.bedi.login.repository.UserRepository;

import com.dodo.bedi.security.CustomAuthenticationFilter;
import com.dodo.bedi.security.JwtConstants;
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

import io.jsonwebtoken.Jwts;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private CustomAuthenticationFilter authenticationFilter;
    private final GoalRepository goalRepository;
    private final RefreshTokenCertificationService refreshTokenCertificationService;

    @Override
    public void setCustomAuthenticationFilter(CustomAuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    // 만료시간 추가
    public Date getExpireTime(String refreshtoken) {


        return Jwts.parser().setSigningKey(JwtConstants.JWT_SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(refreshtoken)
                .getBody()
                .getExpiration();
    }

    // 로그인 처리 후 JWT 토큰 발급

    public TokenDto login(LoginDto loginDto) {
        try {

            //CustomAuthenticationFilter 의 attemptAuthentication 으로 이동
            Authentication authentication = authenticationFilter.attemptAuthentication(loginDto);

            //검증을 마친 이메일 정보
            String email = authentication.getPrincipal().toString();

            //검증을 마친 이메일 정보로 유저 정보 가져오기
            Optional<User> user = userRepository.findByEmail(email);
            String user_id = user.get().getId().toString();

            Optional<User> userId = userRepository.findById(Long.parseLong(user_id));


            //토큰 생성
            String accessToken = JWT.create()
                    .withSubject(user_id)  //  랜덤 값
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.AT_EXP_TIME))  // 토큰 만료시간
                    .withClaim("username",  user.get().getUsername())   //사용자 이름
                    .withIssuedAt(new Date(System.currentTimeMillis()))  // 토큰 생성시간
                    .sign(Algorithm.HMAC256(JwtConstants.JWT_SECRET));  //JWT_SECRET 키로 암호화
            String refreshToken = JWT.create()
                    .withSubject(user_id)
                    .withExpiresAt(new Date(System.currentTimeMillis() + JwtConstants.RT_EXP_TIME))
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .sign(Algorithm.HMAC256(JwtConstants.JWT_SECRET));

            refreshTokenCertificationService.saveRefreshToken(user_id,refreshToken);


            return TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .refreshTokenExpireTime(getExpireTime(refreshToken))
                    .build();

        } catch (AuthenticationException e) {
            throw new CustomAuthenticationException("로그인 잘못됨");
        }
        catch (NullPointerException e) {
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

//    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
//
//        //유저 정보 가져오기
//        User user = userRepository.findById(id)
//                .orElseThrow(() -> new UsernameNotFoundException("UserDetailsService - loadUserByUsername : 사용자를 찾을 수 없습니다."));
//
//        // authorities 대신 Collections.EMPTY_LIST을 넣어 Role 없이 인증가능하게 했다
//        //CustomAuthProvider 의 authenticate 로 복귀
//        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(),Collections.EMPTY_LIST);
//    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {

        //유저 정보 가져오기
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("UserDetailsService - loadUserByUsername : 사용자를 찾을 수 없습니다."));

        // authorities 대신 Collections.EMPTY_LIST을 넣어 Role 없이 인증가능하게 했다
        //CustomAuthProvider 의 authenticate 로 복귀
        return new org.springframework.security.core.userdetails.User(user.getId().toString(), user.getPassword(),Collections.EMPTY_LIST);
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty())
            return null;
        else
            return user.get();

    }

//    @Override
//    public User findUserByEmail(String email) {
//        try {
//            Optional<User> user = userRepository.findByEmail(email);
//            return user.get();
//        }
//        catch (NoSuchElementException e){
//            throw new NoSuchElementException();
//        }
//
//    }


    @Override
    public void saveUser(UserDto dto) throws DuplicateEmailException {
        boolean isSave = validateDuplicateUserEmail(dto);  //검증하고
        if (!isSave) {
            dto.encodePassword(passwordEncoder.encode(dto.getPassword()));  //비밀번호 암호화 해서
            User user = dto.toEntity();
            userRepository.save(user);   //저장하기

        }
    }

    private boolean validateDuplicateUserEmail(UserDto dto) throws DuplicateEmailException {
        if (userRepository.existsByEmail(dto.getEmail())) {     // 이메일로 검사하게끔 수정
            throw new DuplicateEmailException("이미 존재하는 이메일 입니다");
        }
        return false;
    }

    @Override
    public User getUserFromAccessToken() {
        try {
            // Authorization filter에서 SecurityContextHolder에서 set한 authentication 객체를 가져온다.
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userId = authentication.getPrincipal().toString();
            return userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new Exception("db에서 user를 가져올 수 없습니다."));
        } catch (Exception e){
            log.error(String.valueOf(e));
            return null;
        }
    }

    //return 쪽 수정했습니다
    @Override
    public TokenDto refresh(String refreshToken) {

        // === Refresh Token 유효성 검사 === //
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(JwtConstants.JWT_SECRET)).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);

        // === Access Token 재발급 === //
        long now = System.currentTimeMillis();
        String id = decodedJWT.getSubject();

        User user = userRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        if (!refreshTokenCertificationService.findRefreshToken(id).equals(refreshToken) ){
            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
        }

//        if (!user.getRefreshToken().getToken().equals(refreshToken)) {
//            throw new JWTVerificationException("유효하지 않은 Refresh Token 입니다.");
//        }
        String accessToken = JWT.create()
                .withSubject(user.getId().toString())
                .withExpiresAt(new Date(now + JwtConstants.AT_EXP_TIME))
                .sign(Algorithm.HMAC256(JwtConstants.JWT_SECRET));

        // === 현재시간과 Refresh Token 만료날짜를 통해 남은 만료기간 계산 === //
        // === Refresh Token 만료시간 계산해 1개월 미만일 시 refresh token도 발급 === //
        long refreshExpireTime = decodedJWT.getClaim("exp").asLong() * 1000;
        long diffDays = (refreshExpireTime - now) / 1000 / (24 * 3600);
        long diffMin = (refreshExpireTime - now) / 1000 / 60;
        if (diffMin < 5) {
            String newRefreshToken = JWT.create()
                    .withSubject(user.getId().toString())  // 카카오가 넘겨주는 랜덤 값
                    .withExpiresAt(new Date(now + JwtConstants.RT_EXP_TIME))
                    .sign(Algorithm.HMAC256(JwtConstants.JWT_SECRET));
//            user.getRefreshToken().setToken(newRefreshToken);
            refreshTokenCertificationService.saveRefreshToken(id,newRefreshToken);

            return TokenDto.builder()
                    .accessToken(accessToken)
                    .refreshToken(newRefreshToken)
                    .refreshTokenExpireTime(getExpireTime(newRefreshToken))
                    .build();
        }

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken("유효기간이 충분합니다")
                .build();



    }
}
