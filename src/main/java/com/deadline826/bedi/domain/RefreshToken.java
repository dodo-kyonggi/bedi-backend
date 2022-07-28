package com.deadline826.bedi.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor

public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //토큰을 id로 하면 user 테이블에 token 이 외래키로 설정되어 다 보이기때문에 별도의 id 설정

    @Column
    private String token;

    @JoinColumn(name="user_id")
    @OneToOne (fetch = FetchType.LAZY)
    private User user;


//    public void updateRefreshToken(String token, User user) {
//        this.token = token;
//        this.user = user;
//
//    }

}
