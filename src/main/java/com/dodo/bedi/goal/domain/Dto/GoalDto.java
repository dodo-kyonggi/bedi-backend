package com.dodo.bedi.goal.domain.Dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class GoalDto {
    private Long id;
    private LocalDate date;
    private Double lat;
    private Double lon;
    private String title;
    private Boolean success;
}
