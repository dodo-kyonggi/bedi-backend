package com.deadline826.bedi.point.controller;

import com.deadline826.bedi.login.domain.User;
import com.deadline826.bedi.login.service.UserService;
import com.deadline826.bedi.point.service.PointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/point")
public class PointController {

    @Autowired
    PointService pointService;
    @Autowired
    UserService userService;

    @GetMapping
    public Integer getAllPoint() {
        User user = userService.getUserFromAccessToken();
        return pointService.getAccumulatedPoint(user);
    }

}
