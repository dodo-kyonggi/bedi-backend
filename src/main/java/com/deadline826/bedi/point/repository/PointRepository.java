package com.deadline826.bedi.point.repository;

import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    Point findOneByUser(User user);

}