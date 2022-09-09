package com.deadline826.bedi.point.service;

import com.deadline826.bedi.login.Domain.User;

public interface PointService {

    Integer getAccumulatedPoint(User user);
    void save(User user, Integer reward);

}