package com.deadline826.bedi.Goal.Service;

import com.deadline826.bedi.Goal.Domain.Dto.GoalRequestDto;
import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.login.Domain.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface GoalService {

    List<Goal> getTodayGoals(User user, LocalDate date); // 오늘날짜의 목표 불러오기

    Goal isSuccess(User user, GoalRequestDto goalRequestDto);

}
