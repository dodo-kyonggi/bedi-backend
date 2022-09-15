package com.dodo.bedi.login.domain.Dto;

import com.dodo.bedi.login.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class UserDto {
    private Long id;     // 카카오가 넘겨주는 랜덤 값
    private String username;    // 사용자 이름
    private String password;
    private String email;    // 로그인 이메일
    private String phone;


    public User toEntity() {
        return User.builder()
                .id(id)
                .username(username)
                .email(email)
                .password(password)
                .phone(phone)

                .build();
    }

    public void encodePassword(String encodedPassword) {
        this.password = encodedPassword;
    }
}
