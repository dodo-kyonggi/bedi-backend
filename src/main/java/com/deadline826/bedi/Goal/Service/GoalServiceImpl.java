package com.deadline826.bedi.Goal.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Goal.repository.GoalRepository;
import com.deadline826.bedi.login.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService{
    private final GoalRepository goalRepository;

    //추가
    @Override
    public List<Goal> getTodayGoals(User user, LocalDate date){
        List<Goal> goalsOrderByTitleAsc = goalRepository.findByUserAndDateOrderByTitleAsc(user,date);
        return goalsOrderByTitleAsc;
    }

}
