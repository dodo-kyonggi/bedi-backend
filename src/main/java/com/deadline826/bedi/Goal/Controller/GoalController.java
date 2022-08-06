package com.deadline826.bedi.Goal.Controller;

import com.deadline826.bedi.Goal.Domain.Dto.GoalPostDto;
import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Goal.Service.GoalService;
import com.deadline826.bedi.exception.ErrorResponse;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.Goal.Domain.Dto.DateDto;
import com.deadline826.bedi.login.Service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.deadline826.bedi.security.JwtConstants.TOKEN_HEADER_PREFIX;
import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Controller
@RequestMapping("/goal")
@RequiredArgsConstructor
public class GoalController {


    private final UserService userService;
    private final GoalService goalService;

    //목표 보여주기

    @GetMapping("/show")
    @ResponseBody
    public  List<Goal> showGoals(@RequestParam LocalDate date){


        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();


        // 유저정보와 프론트에서 넘겨주는 오늘 날짜를 이용해 오늘의 목표 불러오기
        List<Goal> todayGoals = goalService.getTodayGoals(user,date);

        // 오늘의 목표 리턴
        return todayGoals;

    }

    //목표 등록하기
    @PostMapping("/posting")
    public String postGoal(@RequestBody GoalPostDto goalPostDto, HttpServletResponse response,
                          RedirectAttributes redirectAttributes) throws IOException {

        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();


        //  거리 게산 , 날짜 계산
        boolean isValidDate = goalService.isValidDate(goalPostDto.getDate());
        boolean isValidDistance = goalService.isValidDistance(goalPostDto);

        //새로운 목표를 만들고
        Goal newGoal = new Goal();

        // 날짜와 거리가 유효한지 체크한다
        goalService.checkDateAndDistance(isValidDate,isValidDistance,true,response,newGoal,goalPostDto,user);


        // 오늘 날짜로 리다이렉트
        redirectAttributes.addAttribute("date",LocalDate.now());
        return "redirect:/goal/show";

    }


    //목표 수정하기
    @ResponseBody
    @PostMapping("/edit/{goalId}")
    public ResponseEntity editGoal(@RequestBody GoalPostDto goalPostDto, @PathVariable Long goalId, HttpServletResponse response) throws IOException {

        // accessToken 으로부터 유저정보 불러오기
        User user = userService.getUserFromAccessToken();



        // 수정하길 원하는 목표의 아이디의 받아 목표를 찾는다
        Goal goal = goalService.findGoalById(goalId);


        boolean isValidDate = goalService.isValidDate(goal.getDate());  // 기존에 저장된 날짜가 오늘날짜 혹은 미래일정 인지 확인한다
        boolean isValidDistance = goalService.isValidDistance(goalPostDto);  // 일정거리 이상일때 수정가능
        boolean isEditable = goalService.isValidDate(goalPostDto.getDate());  // 저장 될 날짜가 오늘날짜 보다 커야한다


        // 날짜와 거리가 유효한지 체크한다
        goalService.checkDateAndDistance(isValidDate,isValidDistance,isEditable,response,goal,goalPostDto,user);


        return ResponseEntity.ok("수정 완료");
    }


    //목표 삭제하기
    @ResponseBody
    @GetMapping("/delete/{goalId}")
    public ResponseEntity removeGoal(@PathVariable Long goalId, HttpServletResponse response) throws IOException {

        // 삭제하길 원하는 목표의 아이디의 받아 목표를 찾는다
        Goal goal = goalService.findGoalById(goalId);


        boolean isValidDate = goalService.isValidDate(goal.getDate());  // 기존에 저장된 날짜가 오늘날짜 혹은 미래일정 인지 확인한다

        if (isValidDate){   // 날짜가 유효하다면
            goalService.removeGoal(goal);
        }
        else {    // 날짜가 유효하지 않다면
            response.setStatus(SC_BAD_REQUEST);
            response.setContentType(APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("utf-8");
            ErrorResponse errorResponse = new ErrorResponse(400, "이미 지난 목표는 삭제할수 없습니다");
            new ObjectMapper().writeValue(response.getWriter(), errorResponse);
        }


        return ResponseEntity.ok("삭제 완료");
    }



}
