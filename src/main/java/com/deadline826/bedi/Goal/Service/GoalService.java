package com.deadline826.bedi.Goal.Service;

import com.deadline826.bedi.Goal.Domain.Dto.GoalPostDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalRequestDto;
import com.deadline826.bedi.login.Domain.User;

import java.time.LocalDate;
import java.util.List;

public interface GoalService {

    List<GoalDto> getTodayGoals(User user, LocalDate date); // 오늘날짜의 목표 불러오기

    GoalDto createGoal(User user, GoalPostDto goalPostDto); //목표 저장

    GoalDto updateGoal(GoalPostDto goalPostDto); //목표 수정

    void removeGoal(Long goalId);  //목표 삭제

    GoalDto isSuccess(User user, GoalRequestDto goalRequestDto);

}
