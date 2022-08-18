package com.deadline826.bedi.Statistics.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Slf4j
@Service
public class StaticServiceImpl implements StaticService{

    @Override
    public Double getTotalGoalCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표에 대해서만 가져온다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .count();


    }

    @Override
    public Double getTotalGoalSuccessCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표 중 성공한 목표만 가져온다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getSuccess().equals(true))
                .count();
    }

    @Override
    public Double thisMonthTotalCount(List<Goal> goals){

        // 이번 달 목표 전체를 불러온다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() ).count();
    }

    @Override
    public Double selectMonthTotalCount(List<Goal> goals, int year , Month month){

        // 선택한 달 목표 전체를 불러온다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month ).count();
    }

    @Override
    public Double thisMonthTotalSuccessCount(List<Goal> goals){

        // 이번 달 목표 전체 중 성공한 목표만 불러온다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() &&
                goal.getSuccess().equals(true) ).count();
    }

    @Override
    public Double selectMonthTotalSuccessCount(List<Goal> goals, int year , Month month){

        // 선택한 달 목표 전체 중 성공한 목표만 불러온다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month &&
                goal.getSuccess().equals(true) ).count();
    }

    public String totalPercent(Double totalGoalCount , Double totalGoalSuccessCount){

        return (int)((totalGoalSuccessCount/totalGoalCount)*100) + "%";
    }

    public String thisMonthPercent(Double thisMonthTotalCount , Double thisMonthTotalSuccessCount){


        return (int)((thisMonthTotalSuccessCount/thisMonthTotalCount)*100) + "%";
    }

    public String selectMonthPercent(Double selectMonthTotalCount , Double selectMonthTotalSuccessCount){


        return (int)((selectMonthTotalSuccessCount/selectMonthTotalCount)*100) + "%";
    }


}
