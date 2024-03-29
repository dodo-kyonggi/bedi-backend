package com.dodo.bedi.login.domain;

import com.dodo.bedi.goal.domain.Goal;

import com.dodo.bedi.point.domain.Point;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class User {

    @Id
    @Column(name="user_id")
    private Long id;       // 랜덤 값

    private String username;   // 사용자 이름

    private String password;  //인코딩 된 값

    private String email;  // 이메일

    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Goal> goals=new ArrayList<>();   // 양방향 매핑 (테이블에는 표시 안됨)

    @OneToOne(mappedBy = "user")
    private Point point;

}
