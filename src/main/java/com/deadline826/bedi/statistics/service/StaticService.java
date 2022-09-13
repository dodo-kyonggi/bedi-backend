package com.deadline826.bedi.statistics.service;

import com.deadline826.bedi.goal.domain.Goal;
import com.deadline826.bedi.statistics.domain.Dto.TotalStatisticsDto;
import com.deadline826.bedi.statistics.domain.Dto.SelectMonthAchievePercentDto;

import java.time.Month;
import java.util.List;

public interface StaticService {

    TotalStatisticsDto getTotalStatistics(List<Goal> goals, Long userId);

    SelectMonthAchievePercentDto getSelectMonthAchievePercent(List<Goal> goals, int year, Month month);
}
