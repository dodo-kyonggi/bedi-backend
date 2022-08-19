package com.deadline826.bedi.Statistics.Controller;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Statistics.Domain.Dto.GoalsAchievePercentDto;
import com.deadline826.bedi.Statistics.Domain.Dto.SelectMonthAchievePercentDto;
import com.deadline826.bedi.Statistics.Service.StaticService;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.login.Service.UserService;
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

    // 홈 화면에서 전체 목표 달성 비율과 이번 달 목표 달성 비율을 보여준다
    @GetMapping("/home")
    public GoalsAchievePercentDto showHomeStatic(){
        User user = userService.getUserFromAccessToken();
        List<Goal> goals = user.getGoals();

        GoalsAchievePercentDto thisMonthAndTotalAchievePercent = staticService.getThisMonthAndTotalAchievePercent(goals);

        return thisMonthAndTotalAchievePercent;

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
