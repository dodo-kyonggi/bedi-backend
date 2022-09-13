package com.deadline826.bedi.point.service;

import com.deadline826.bedi.login.domain.User;

public interface PointService {

    Integer getAccumulatedPoint(User user);
    void save(User user, Integer reward);

}