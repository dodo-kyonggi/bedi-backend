package com.dodo.bedi.point.service;

import com.dodo.bedi.login.domain.User;

public interface PointService {

    Integer getAccumulatedPoint(User user);
    void save(User user, Integer reward);

}