package com.dodo.bedi.point.controller;

import com.dodo.bedi.login.domain.User;
import com.dodo.bedi.login.service.UserService;
import com.dodo.bedi.point.service.PointService;
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
