package com.deadline826.bedi.goal.repository;

import com.deadline826.bedi.goal.domain.Goal;
import com.deadline826.bedi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface GoalRepository  extends JpaRepository<Goal, Long> {

    // Goal 테이블에서 유저정보와 날짜가 일치하는 목표를 찾아 제목순으로 정렬하여 리스트로 반환
    List<Goal> findByUserAndDateOrderByTitleAsc(User user, LocalDate date);
}
