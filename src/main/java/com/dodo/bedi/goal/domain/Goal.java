package com.dodo.bedi.goal.domain;

import com.dodo.bedi.login.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
@Entity
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private Double lat;   // 위도
    private Double lon;   // 경도

    private String title; // 제목

    private Boolean success;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;             // 연관관게 주인

    @Builder
    public Goal(LocalDate date, Double lat, Double lon, String title, User user) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.title = title;
        this.success = false;
        this.user = user;
    }

    public void update(LocalDate date, Double lat, Double lon, String title) {
        this.date = date;
        this.lat = lat;
        this.lon = lon;
        this.title = title;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
