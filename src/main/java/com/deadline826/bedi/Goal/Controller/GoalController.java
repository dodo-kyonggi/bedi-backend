package com.deadline826.bedi.Goal.Controller;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Goal.Service.GoalService;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.Goal.Domain.Dto.DateDto;
import com.deadline826.bedi.login.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.deadline826.bedi.security.JwtConstants.TOKEN_HEADER_PREFIX;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {

    private final UserService userService;
    private final GoalService goalService;

    @GetMapping
    public List<Goal> showGoals(@RequestBody DateDto dateDto, HttpServletRequest request){

        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();

        // 유저정보와 프론트에서 넘겨주는 오늘 날짜를 이용해 오늘의 목표 불러오기
        List<Goal> todayGoals = goalService.getTodayGoals(user,dateDto.getDate());
        return todayGoals;

    }


}
