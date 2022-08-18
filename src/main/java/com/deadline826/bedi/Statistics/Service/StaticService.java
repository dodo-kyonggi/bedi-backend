package com.deadline826.bedi.Statistics.Service;

import com.deadline826.bedi.Goal.Domain.Goal;

import java.time.Month;
import java.util.List;

public interface StaticService {

    Double getTotalGoalCount(List<Goal> goals);

    Double getTotalGoalSuccessCount(List<Goal> goals);

    Double thisMonthTotalCount(List<Goal> goals);

    Double thisMonthTotalSuccessCount(List<Goal> goals);

    Double selectMonthTotalCount(List<Goal> goals, int year , Month month);

    Double selectMonthTotalSuccessCount(List<Goal> goals, int year , Month month);

    String totalPercent(Double totalGoalCount , Double totalGoalSuccessCount);

    String thisMonthPercent(Double thisMonthTotalCount , Double thisMonthTotalSuccessCount);

    String selectMonthPercent(Double selectMonthTotalCount , Double selectMonthTotalSuccessCount);
}
