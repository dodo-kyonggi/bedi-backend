package com.deadline826.bedi.statistics.controller;

import com.deadline826.bedi.goal.domain.Goal;
import com.deadline826.bedi.statistics.domain.Dto.TotalStatisticsDto;
import com.deadline826.bedi.statistics.domain.Dto.SelectMonthAchievePercentDto;
import com.deadline826.bedi.statistics.service.StaticService;
import com.deadline826.bedi.login.domain.User;
import com.deadline826.bedi.login.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Month;
import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticController {

    private final UserService userService;
    private final StaticService staticService;

    // 홈 화면에서 전반적인 통계를 보여준다
    @GetMapping("/home")
    public TotalStatisticsDto showHomeStatic(){
        User user = userService.getUserFromAccessToken();
        List<Goal> goals = user.getGoals();

        TotalStatisticsDto totalStatistics = staticService.getTotalStatistics(goals,user.getId());

        return totalStatistics;

    }


    // 선택한 달의 목표 달성 비율을 보여준다
    @GetMapping("/{year}/{month}")
    public SelectMonthAchievePercentDto showSelectedMonthStatic(@PathVariable int year, @PathVariable Month month){
        User user = userService.getUserFromAccessToken();
        List<Goal> goals = user.getGoals();

        SelectMonthAchievePercentDto selectMonthAchievePercent = staticService.getSelectMonthAchievePercent(goals, year, month);


        return selectMonthAchievePercent;

    }
}
