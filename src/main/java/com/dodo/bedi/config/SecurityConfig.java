package com.dodo.bedi.config;

import com.dodo.bedi.login.service.UserService;
import com.dodo.bedi.security.CustomAuthenticationFilter;
import com.dodo.bedi.security.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthorizationFilter customAuthorizationFilter;
    private final AccessDeniedHandler accessDeniedHandler;
    public CustomAuthenticationFilter customAuthenticationFilter;
    private final UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //CustomAuthenticationFilter 객체 생성
        customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());

        userService.setCustomAuthenticationFilter(customAuthenticationFilter);

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 X

        // 로그인, 리프레시 요청이라면 모두 허용

        http.authorizeRequests().antMatchers("/","/message/send","/message/confirm").permitAll();
        http.authorizeRequests().antMatchers("/user/login","/user/signup","/user/refresh").permitAll();
        //그 외는 토큰으로 인증 받아야함
        http.authorizeRequests().anyRequest().authenticated();

        // http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        //http.addFilter(customAuthorizationFilter);

        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
    }


    //AuthenticationManager 생성
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        AuthenticationManager authenticationManager = super.authenticationManagerBean();
        return authenticationManager;
    }
}
