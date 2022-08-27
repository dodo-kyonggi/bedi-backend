package com.deadline826.bedi.Statistics.Domain.Dto;

import lombok.Getter;
import lombok.Setter;


import java.util.LinkedHashMap;

@Getter
@Setter
public class TotalStatisticsDto {
    public String totalPercent;
    public String thisMonthPercent;
    public String showIncreaseOrDecrease;
    public String totalSuccessCount;
    private String topRank;
    private LinkedHashMap last7DaysPercent;
}
