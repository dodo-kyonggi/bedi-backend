package com.deadline826.bedi.goal.domain.Dto;

import com.deadline826.bedi.goal.domain.Goal;
import com.deadline826.bedi.login.domain.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GoalPostDto {

    private Long goalId;

    private LocalDate date;  // "yyyy-mm-dd" 형식으로 받기

    private String title;

    private Double arrive_lat;     // 도착지 위도
    private Double arrive_lon;     // 도착지 경도

    private Double start_lat;      // 출발지 위도
    private Double start_lon;      // 출발지 경도

    public Goal toEntity(User user) {
        return Goal.builder()
                .date(this.date)
                .title(this.title)
                .lat(this.arrive_lat)
                .lon(this.arrive_lon)
                .user(user)
                .build();
    }
}
