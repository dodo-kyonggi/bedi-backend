package com.deadline826.bedi.Goal.Domain;

import com.deadline826.bedi.login.Domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Setter
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date= LocalDate.now();

    private Double x_coordinate;   // x 좌표값
    private Double y_coordinate;   // y 좌표값

    private String title;          // 제목

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;             // 연관관게 주인
}
