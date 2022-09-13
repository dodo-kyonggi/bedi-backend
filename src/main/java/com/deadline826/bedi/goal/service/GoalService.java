package com.deadline826.bedi.goal.service;

import com.deadline826.bedi.goal.domain.Dto.GoalPostDto;
import com.deadline826.bedi.goal.domain.Dto.GoalDto;
import com.deadline826.bedi.goal.domain.Dto.GoalRequestDto;
import com.deadline826.bedi.login.domain.User;

import java.time.LocalDate;
import java.util.List;

public interface GoalService {

    List<GoalDto> getTodayGoals(User user, LocalDate date); // 오늘날짜의 목표 불러오기

    GoalDto createGoal(User user, GoalPostDto goalPostDto); //목표 저장

    GoalDto updateGoal(GoalPostDto goalPostDto); //목표 수정

    void removeGoal(Long goalId);  //목표 삭제

    GoalDto isSuccess(User user, GoalRequestDto goalRequestDto);

}
