package com.deadline826.bedi.point.domain;

import com.deadline826.bedi.login.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table (name="point")
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name="point_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Integer reward;

    public void setReward(Integer reward) {
        this.reward += reward;
    }

    @Builder
    public Point(User user, Integer reward) {
        this.user = user;
        this.reward = reward;
    }

}