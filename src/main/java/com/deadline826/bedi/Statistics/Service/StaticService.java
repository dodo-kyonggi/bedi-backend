package com.deadline826.bedi.Statistics.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Statistics.Domain.Dto.TotalStatisticsDto;
import com.deadline826.bedi.Statistics.Domain.Dto.SelectMonthAchievePercentDto;

import java.time.Month;
import java.util.List;

public interface StaticService {

    TotalStatisticsDto getTotalStatistics(List<Goal> goals, Long userId);

    SelectMonthAchievePercentDto getSelectMonthAchievePercent(List<Goal> goals, int year, Month month);
}
