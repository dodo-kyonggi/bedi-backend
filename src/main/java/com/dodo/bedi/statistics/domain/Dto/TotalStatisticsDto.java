package com.dodo.bedi.statistics.domain.Dto;

import lombok.Getter;
import lombok.Setter;


import java.util.LinkedHashMap;

@Getter
@Setter
public class TotalStatisticsDto {
    public int totalPercent;
    public int thisMonthPercent;
    private int lastMonthPercent;
    public int showIncreaseOrDecrease;
    public int totalSuccessCount;
    private int topRank;
    private LinkedHashMap last7DaysPercent;
}
