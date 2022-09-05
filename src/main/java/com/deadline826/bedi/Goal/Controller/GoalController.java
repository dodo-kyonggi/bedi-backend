package com.deadline826.bedi.Goal.Controller;


import com.deadline826.bedi.Goal.Domain.Dto.GoalPostDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalRequestDto;
import com.deadline826.bedi.Goal.Service.GoalService;
import com.deadline826.bedi.character.service.CharacterService;
import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.login.Service.UserService;

import com.deadline826.bedi.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {


    private final UserService userService;
    private final GoalService goalService;
    private final CharacterService characterService;
    private final PointService pointService;

    //목표 보여주기
    @GetMapping("/show")
    public ResponseEntity<List<GoalDto>> showGoals(@RequestParam @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date, HttpServletRequest request){

        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();


        // 유저정보와 프론트에서 넘겨주는 오늘 날짜를 이용해 오늘의 목표 불러오기
        List<GoalDto> todayGoals = goalService.getTodayGoals(user,date);

        return ResponseEntity.ok().body(todayGoals);
    }

    //목표 등록하기
    @PostMapping("/create")
    public ResponseEntity<GoalDto> postGoal(@RequestBody GoalPostDto goalPostDto) {

        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();

        GoalDto goalDto = goalService.createGoal(user, goalPostDto);

        return ResponseEntity.ok().body(goalDto);
    }


    //목표 수정하기
    @PutMapping("/update")
    public ResponseEntity<GoalDto> updateGoal(@RequestBody GoalPostDto goalPostDto) {

        GoalDto goal = goalService.updateGoal(goalPostDto);

        return ResponseEntity.ok().body(goal);
    }


    //목표 삭제하기
    @GetMapping("/delete/{goalId}")
    public ResponseEntity removeGoal(@PathVariable Long goalId){

        goalService.removeGoal(goalId);

        return ResponseEntity.ok("삭제가 완료되었습니다.");
    }

    @PostMapping("/success")
    public ResponseEntity<Map> isSuccess(@RequestBody GoalRequestDto goalRequestDto) {

        User user = userService.getUserFromAccessToken();

        Map<String, Object> response = new HashMap<>();

        GoalDto goalDto = goalService.isSuccess(user, goalRequestDto);

        response.put("goal", goalDto);

        Integer point = pointService.getAccumulatedPoint(user.getId());

        CharacterDto characterDto = characterService.reachToNextLevel(user, point);

        if (characterDto != null) {
            response.put("levelUp", true);
            response.put("character", characterDto);
        } else response.put("levelUp", false);

        return ResponseEntity.ok().body(response);
    }

}
