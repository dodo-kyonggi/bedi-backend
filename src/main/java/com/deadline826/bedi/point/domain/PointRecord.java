package com.deadline826.bedi.point.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@NoArgsConstructor
public class PointRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "point_reward_id")
    private Long id;

    @CreatedDate
    private LocalDateTime createdDate;

    private Integer reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="point_id")
    private Point point;

    @Builder
    public PointRecord(Integer reward, Point point) {
        this.reward = reward;
        this.point = point;
    }

}
