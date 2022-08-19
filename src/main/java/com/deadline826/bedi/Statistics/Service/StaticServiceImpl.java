package com.deadline826.bedi.Statistics.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Statistics.Domain.Dto.GoalsAchievePercentDto;
import com.deadline826.bedi.Statistics.Domain.Dto.SelectMonthAchievePercentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Slf4j
@Service
public class StaticServiceImpl implements StaticService{




    @Override
    public SelectMonthAchievePercentDto getSelectMonthAchievePercent(List<Goal> goals,int year, Month month){

        Double selectMonthTotalCount = selectMonthTotalCount(goals, year, month);
        Double selectMonthTotalSuccessCount = selectMonthTotalSuccessCount(goals, year, month);

        String selectMonthPercent = selectMonthPercent(selectMonthTotalCount, selectMonthTotalSuccessCount);


        SelectMonthAchievePercentDto selectMonthAchievePercentDto = new SelectMonthAchievePercentDto();
        selectMonthAchievePercentDto.setAchievePercent(selectMonthPercent);

        return selectMonthAchievePercentDto;
    }


    @Override
    public GoalsAchievePercentDto getThisMonthAndTotalAchievePercent(List<Goal> goals){

        Double totalGoalCount = getTotalGoalCount(goals);
        Double totalGoalSuccessCount = getTotalGoalSuccessCount(goals);
        Double thisMonthTotalCount = thisMonthTotalCount(goals);
        Double thisMonthTotalSuccessCount = thisMonthTotalSuccessCount(goals);

        String totalPercent = totalPercent(totalGoalCount, totalGoalSuccessCount);
        String thisMonthPercent = thisMonthPercent(thisMonthTotalCount, thisMonthTotalSuccessCount);

        GoalsAchievePercentDto goalsAchievePercentDto = new GoalsAchievePercentDto();

        goalsAchievePercentDto.setTotalAchievePercent(totalPercent);
        goalsAchievePercentDto.setThisMonthAchievePercent(thisMonthPercent);

        return goalsAchievePercentDto;

    }



    public Double getTotalGoalCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표의 총 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .count();


    }


    public Double getTotalGoalSuccessCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getSuccess().equals(true))
                .count();
    }


    public Double thisMonthTotalCount(List<Goal> goals){

        // 이번 달 목표의 총 갯수를 리턴한다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() ).count();
    }


    public Double selectMonthTotalCount(List<Goal> goals, int year , Month month){

        // 선택한 달의 목표 총 갯수를 리턴한다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month ).count();
    }


    public Double thisMonthTotalSuccessCount(List<Goal> goals){

        // 이번 달 목표 전체 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() &&
                goal.getSuccess().equals(true) ).count();
    }


    public Double selectMonthTotalSuccessCount(List<Goal> goals, int year , Month month){

        // 선택한 달 목표 전체 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream().filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month &&
                goal.getSuccess().equals(true) ).count();
    }


    public String totalPercent(Double totalGoalCount , Double totalGoalSuccessCount){

        // 전체 목표 달성 비율을 보여준다
        return (int)((totalGoalSuccessCount/totalGoalCount)*100) + "%";
    }


    public String thisMonthPercent(Double thisMonthTotalCount , Double thisMonthTotalSuccessCount){


        // 이번 달의 목표 달성 비율을 보여준다
        return (int)((thisMonthTotalSuccessCount/thisMonthTotalCount)*100) + "%";
    }


    public String selectMonthPercent(Double selectMonthTotalCount , Double selectMonthTotalSuccessCount){


        // 선택한 달의 목표 달성 비율을 보여준다
        return (int)((selectMonthTotalSuccessCount/selectMonthTotalCount)*100) + "%";
    }


}
