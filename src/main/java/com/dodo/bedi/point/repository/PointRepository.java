package com.dodo.bedi.point.repository;

import com.dodo.bedi.login.domain.User;
import com.dodo.bedi.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Point findOneByUser(User user);

}