package com.deadline826.bedi.point.service;

import com.deadline826.bedi.login.domain.User;
import com.deadline826.bedi.point.domain.Point;
import com.deadline826.bedi.point.domain.PointRecord;
import com.deadline826.bedi.point.repository.PointRecordRepository;
import com.deadline826.bedi.point.repository.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Repository
public class PointServiceImpl implements PointService {

    @Autowired
    PointRepository pointRepository;

    @Autowired
    PointRecordRepository pointRecordRepository;

    @Override
    @Transactional(readOnly = true)
    public Integer getAccumulatedPoint(User user) {
        Point point = pointRepository.findOneByUser(user);
        if (point == null) return 0;
        else return point.getReward();
    }

    @Override
    @Transactional
    public void save(User user, Integer reward) {

        Point point = setUp(user);

        point.setReward(reward);

        PointRecord pointRecord = PointRecord.builder()
                .point(point)
                .reward(reward)
                .build();

        pointRecordRepository.save(pointRecord);

    }

    private Point setUp(User user) {
        Point point = pointRepository.findOneByUser(user);

        if (point == null) {
            Point userPoint = Point.builder()
                    .user(user)
                    .reward(0)
                    .build();

            return pointRepository.save(userPoint);
        } else return point;
    }
}
