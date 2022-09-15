package com.dodo.bedi.point.repository;

import com.dodo.bedi.login.domain.QUser;
import com.dodo.bedi.point.domain.QPoint;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class PointRepositoryImpl implements CustomPointRepository {

    private final JPAQueryFactory queryfactory;
    private final QPoint point = QPoint.point;
    private final QUser user = QUser.user;

    @Override
    public List<Integer> getAccumulatedPointFromUser(Long userId) {
        return queryfactory.select(point.reward.sum())
                .from(point)
                .where(point.user.id.eq(userId))
                .fetch();
    }

}