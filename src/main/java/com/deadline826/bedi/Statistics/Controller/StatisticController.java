package com.deadline826.bedi.Statistics.Controller;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Statistics.Domain.GoalsAchievePercentDto;
import com.deadline826.bedi.Statistics.Domain.SelectMonthAchievePercentDto;
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

    @GetMapping("/home")
    public GoalsAchievePercentDto showHomeStatic(){
        User user = userService.getUserFromAccessToken();
        List<Goal> goals = user.getGoals();

        Double totalGoalCount = staticService.getTotalGoalCount(goals);

        Double totalGoalSuccessCount = staticService.getTotalGoalSuccessCount(goals);

        Double thisMonthTotalCount = staticService.thisMonthTotalCount(goals);

        Double thisMonthTotalSuccessCount = staticService.thisMonthTotalSuccessCount(goals);

        String totalPercent = staticService.totalPercent(totalGoalCount, totalGoalSuccessCount);

        String thisMonthPercent = staticService.thisMonthPercent(thisMonthTotalCount, thisMonthTotalSuccessCount);


        GoalsAchievePercentDto GoalsAchievePercentDto = new GoalsAchievePercentDto();

        GoalsAchievePercentDto.setAllAchievePercent(totalPercent);
        GoalsAchievePercentDto.setMonthAchievePercent(thisMonthPercent);


        return GoalsAchievePercentDto;

    }

//    @GetMapping("/all")
//    public AchievePercentDto showAllStatic(){
//        User user = userService.getUserFromAccessToken();
//        List<Goal> goals = user.getGoals();
//        long allCount = goals.stream().count();
//        long successCount = goals.stream().filter(goal -> goal.getSuccess().equals(true)).count();
//        AchievePercentDto achievePercentDto = new AchievePercentDto();
//        achievePercentDto.setAchievePercent(successCount/allCount);
//
//        return achievePercentDto;
//
//    }

    @GetMapping("/{year}/{month}")
    public SelectMonthAchievePercentDto showSelectedMonthStatic(@PathVariable int year, @PathVariable Month month){
        User user = userService.getUserFromAccessToken();
        List<Goal> goals = user.getGoals();

        Double selectMonthTotalCount = staticService.selectMonthTotalCount(goals, year, month);
        Double selectMonthTotalSuccessCount = staticService.selectMonthTotalSuccessCount(goals, year, month);

        String selectMonthPercent = staticService.selectMonthPercent(selectMonthTotalCount, selectMonthTotalSuccessCount);


        SelectMonthAchievePercentDto selectMonthAchievePercentDto = new SelectMonthAchievePercentDto();
        selectMonthAchievePercentDto.setAchievePercent(selectMonthPercent);


        return selectMonthAchievePercentDto;

    }
}
