package com.deadline826.bedi.Statistics.Service;

import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Statistics.Domain.Dto.TotalStatisticsDto;
import com.deadline826.bedi.Statistics.Domain.Dto.SelectMonthAchievePercentDto;
import com.deadline826.bedi.Statistics.Repository.CompleteRepository;
import com.deadline826.bedi.login.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaticServiceImpl implements StaticService{


    private final UserRepository userRepository;
    private final CompleteRepository completeRepository;




    @Override
    public SelectMonthAchievePercentDto getSelectMonthAchievePercent(List<Goal> goals,int year, Month month){

        Double selectMonthTotalCount = selectMonthTotalCount(goals, year, month);
        Double selectMonthTotalSuccessCount = selectMonthTotalSuccessCount(goals, year, month);

        int selectMonthPercent = selectMonthPercent(selectMonthTotalCount, selectMonthTotalSuccessCount);


        SelectMonthAchievePercentDto selectMonthAchievePercentDto = new SelectMonthAchievePercentDto();
        selectMonthAchievePercentDto.setAchievePercent(selectMonthPercent+"%");

        return selectMonthAchievePercentDto;
    }


    @Override
    public TotalStatisticsDto getTotalStatistics(List<Goal> goals, Long userId){

        String showIncreaseOrDecrease; //달성률 증감 표시

        Double totalGoalCount = getTotalGoalCount(goals);
        int totalSuccessCount = getTotalGoalSuccessCount(goals);

        Double thisMonthTotalCount = thisMonthTotalCount(goals);
        Double thisMonthTotalSuccessCount = thisMonthTotalSuccessCount(goals);

        Double lastMonthTotalCount = lastMonthTotalCount(goals);
        Double lastMonthTotalSuccessCount = lastMonthTotalSuccessCount(goals);

        int totalPercent = totalPercent(totalGoalCount, (double)totalSuccessCount);
        int thisMonthPercent = thisMonthPercent(thisMonthTotalCount, thisMonthTotalSuccessCount);
        int lastMonthPercent = lastMonthPercent(lastMonthTotalCount, lastMonthTotalSuccessCount);

        int difference = Math.abs(thisMonthPercent - lastMonthPercent); // 지난달과 이번달의 달성률 차이



        if (thisMonthPercent>lastMonthPercent){
            showIncreaseOrDecrease = "지난 달 보다 달성률이 " +difference+"% 만큼 증가했습니다";
        }
        else if( thisMonthPercent == lastMonthPercent){
            showIncreaseOrDecrease = "지난 달과 달성률이 동일합니다";
        }
        else{
            showIncreaseOrDecrease = "지난 달 보다 달성률이 " +difference+"% 만큼 하락했습니다";
        }





        List<Long> userCount = userRepository.getUserCount();
        Double numberOfUser = (double)userCount.get(0);  //총 유저수





        Double rank = Double.parseDouble(completeRepository.getRank(userId,numberOfUser));
        int topRank = (int) ( (rank / numberOfUser) * 100);  //나의 상위 랭크를 표시


        LinkedHashMap last7DaysPercent = last7DaysPercent(goals);




        TotalStatisticsDto totalStatisticsDto = new TotalStatisticsDto();

        totalStatisticsDto.setTotalPercent(totalPercent+"%");
        totalStatisticsDto.setThisMonthPercent(thisMonthPercent+"%");
        totalStatisticsDto.setShowIncreaseOrDecrease(showIncreaseOrDecrease);
        totalStatisticsDto.setTotalSuccessCount( totalSuccessCount + "개" );
        totalStatisticsDto.setTopRank(topRank +"%");
        totalStatisticsDto.setLast7DaysPercent(last7DaysPercent);

        return totalStatisticsDto;

    }



    public Double getTotalGoalCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표의 총 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .count();


    }


    public int getTotalGoalSuccessCount(List<Goal> goals){

        // 현재 날짜보다 이전이거나 현재날짜인 목표 중 성공한 목표의 갯수를 리턴한다
        return (int)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getSuccess().equals(true))
                .count();
    }


    public Double thisMonthTotalCount(List<Goal> goals){

        // 이번 달 목표의 총 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() ).count();
    }

    public Double lastMonthTotalCount(List<Goal> goals){

        // 지난 달 목표의 총 갯수를 리턴한다
        return (double)goals.stream()
//                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                        goal.getDate().getMonth() == LocalDate.now().getMonth().minus(1) )
                .count();
    }


    public Double selectMonthTotalCount(List<Goal> goals, int year , Month month){

        // 선택한 달의 목표 총 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()  ) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month ).count();
    }


    public Double thisMonthTotalSuccessCount(List<Goal> goals){

        // 이번 달 목표 전체 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                goal.getDate().getMonth() == LocalDate.now().getMonth() &&
                goal.getSuccess().equals(true) )
                .count();
    }

    public Double lastMonthTotalSuccessCount(List<Goal> goals){

        // 지난 달 목표 전체 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream()
//                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == LocalDate.now().getYear() &&
                        goal.getDate().getMonth() == LocalDate.now().getMonth().minus(1) &&
                        goal.getSuccess().equals(true) )
                .count();
    }


    public Double selectMonthTotalSuccessCount(List<Goal> goals, int year , Month month){

        // 선택한 달 목표 전체 중 성공한 목표의 갯수를 리턴한다
        return (double)goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) || goal.getDate().isEqual(LocalDate.now()) )
                .filter(goal -> goal.getDate().getYear() == year &&
                goal.getDate().getMonth() == month &&
                goal.getSuccess().equals(true) )
                .count();
    }

    public LinkedHashMap last7DaysPercent(List<Goal> goals){

        List<Goal> goalList = goals.stream()
                .filter(goal -> goal.getDate().isBefore(LocalDate.now()) && goal.getDate().getYear() == LocalDate.now().getYear())
                .collect(Collectors.toList());

        LinkedHashMap<Object, Object> last7DaysPercentMap = new LinkedHashMap<>();

        for (int i= 1; i<=7 ; i++ ){
            int minusDay = i;

            double totalCount = goalList.stream()
                    .filter(goal -> goal.getDate().isBefore(LocalDate.now()) && goal.getDate().getYear() == LocalDate.now().getYear())
                    .filter(goal -> goal.getDate().getDayOfYear() == ((LocalDate.now().getDayOfYear()) - minusDay))
                    .count();

            double successCount = goalList.stream()
                    .filter(goal -> goal.getDate().isBefore(LocalDate.now()) && goal.getDate().getYear() == LocalDate.now().getYear())
                    .filter(goal -> goal.getDate().getDayOfYear() == ((LocalDate.now().getDayOfYear()) - minusDay))
                    .filter(goal -> goal.getSuccess().equals(true))
                    .count();


            String percent = (int)((successCount/totalCount)*100) + "%";

            last7DaysPercentMap.put( i+"daysAgo" ,percent);



        }
        return last7DaysPercentMap;

    }



    public Integer totalPercent(Double totalGoalCount , Double totalGoalSuccessCount){

        // 전체 목표 달성 비율을 보여준다
        return (int)((totalGoalSuccessCount/totalGoalCount)*100) ;
    }


    public Integer thisMonthPercent(Double thisMonthTotalCount , Double thisMonthTotalSuccessCount){


        // 이번 달의 목표 달성 비율을 보여준다
        return (int)((thisMonthTotalSuccessCount/thisMonthTotalCount)*100);
    }

    public Integer lastMonthPercent(Double lastMonthTotalCount , Double lastMonthTotalSuccessCount){


        // 이번 달의 목표 달성 비율을 보여준다
        return (int)((lastMonthTotalSuccessCount/lastMonthTotalCount)*100);
    }


    public Integer selectMonthPercent(Double selectMonthTotalCount , Double selectMonthTotalSuccessCount){


        // 선택한 달의 목표 달성 비율을 보여준다
        return (int)((selectMonthTotalSuccessCount/selectMonthTotalCount)*100) ;
    }

    public Integer compareThisMonthPercentAndLastMonthPercent(Double selectMonthTotalCount , Double selectMonthTotalSuccessCount){


        // 선택한 달의 목표 달성 비율을 보여준다
        return (int)((selectMonthTotalSuccessCount/selectMonthTotalCount)*100) ;
    }


}
