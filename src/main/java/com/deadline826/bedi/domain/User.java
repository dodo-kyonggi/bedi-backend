package com.deadline826.bedi.domain;

import lombok.*;

import javax.persistence.*;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Setter
public class User {

    @Id
    @Column(name="user_id")
    private Long id;       // 카카오가 넘겨주는 랜덤 값

    private String username;   // 사용자 이름

    private String password;

    private String email;  //카카오 이메일

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @OneToOne
    @JoinColumn (name = "refresh_id")
    private RefreshToken refreshToken;

//    private String refreshToken;

    public void updateRefreshToken(RefreshToken newToken) {
        this.refreshToken = newToken;
    }
}
