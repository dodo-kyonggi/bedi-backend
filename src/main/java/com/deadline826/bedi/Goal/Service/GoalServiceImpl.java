package com.deadline826.bedi.Goal.Service;

import com.deadline826.bedi.Goal.Domain.Dto.GoalPostDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalDto;
import com.deadline826.bedi.Goal.Domain.Dto.GoalRequestDto;
import com.deadline826.bedi.Goal.Domain.Goal;
import com.deadline826.bedi.Goal.exception.*;
import com.deadline826.bedi.Goal.repository.GoalRepository;
import com.deadline826.bedi.Statistics.Domain.Complete;
import com.deadline826.bedi.Statistics.Repository.CompleteRepository;
import com.deadline826.bedi.login.Domain.User;

import com.deadline826.bedi.point.domain.Point;
import com.deadline826.bedi.point.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {

    private final GoalRepository goalRepository;
    private final ModelMapper modelMapper;
    private final CompleteRepository completeRepository;

    /**
     * 현재 날짜의 목표를 보여준다.
     * @param user 사용자
     * @param date 현재 날짜
     * @return List<GoalDto>
     */
    @Override
    public List<GoalDto> getTodayGoals(User user, LocalDate date){
        List<Goal> goalsOrderByTitleAsc = goalRepository.findByUserAndDateOrderByTitleAsc(user,date);
        List<GoalDto> goalDtosList = goalsOrderByTitleAsc.stream()
                .map(goal -> modelMapper.map(goal, GoalDto.class))
                .collect(Collectors.toList());
        return goalDtosList;
    }

    /**
     * 목표 달성 여부
     * DB에서 목표 아이디를 통해 목표를 꺼낸다.
     * 예외 : DB에 없는 목표 아이디가 들어왔다. 현재 위치와 목표 위치가 50m를 넘었다.
     * 20 포인트 적립. 목표 성취로 변경.
     * @param user 사용자
     * @param goalRequestDto (목표 아이디, 현재 위치)
     * @return GoalDto (목표 아이디, 날짜, 제목, 목표 위치)
     */
    @Override
    public GoalDto isSuccess(User user, GoalRequestDto goalRequestDto) {

            Goal goal = goalRepository.findById(goalRequestDto.getGoalId())
                    .orElseThrow(() -> new WrongGoalIDException("잘못된 목표 아이디 입니다."));

            LocalDate date = goal.getDate();
            if (date.compareTo(LocalDate.now()) < 0) {
                throw new PastModifyException("과거 날짜의 목표는 달성할 수 없습니다.");
            }

            else if (date.compareTo(LocalDate.now()) > 0){
                throw new FutureModifyException("미래 날짜의 목표는 달성할 수 없습니다.");
            }

            Double goalLat = goal.getLat();
            Double goalLon = goal.getLon();
            Double nowLat = goalRequestDto.getNowLat();
            Double nowLon = goalRequestDto.getNowLon();

            Double distance = distance(goalLat, goalLon, nowLat, nowLon);

            // 사용자가 목표 범위(50m) 이내에 존재 안함
            if (Double.compare(distance, 50.0) > 0) throw new OutRangeOfGoalException("목표 범위로부터 너무 멉니다.");

            goal.setSuccess(true);

             Optional<Complete> userHasAchieved = completeRepository.findById(user.getId());
        System.out.println("userHasAchieved = " + userHasAchieved);

             if (userHasAchieved.isEmpty()) {
                 Complete complete = Complete.builder()
                         .id(user.getId())
                         .completeCount(1L)
                         .build();
                 //complete.setCompleteCount(complete.getCompleteCount() + 1);
                 completeRepository.save(complete);
             }
             else{
                 Complete isAchieved = userHasAchieved.get();
                isAchieved.setCompleteCount(isAchieved.getCompleteCount()+1);
             }



            return modelMapper.map(goal, GoalDto.class);
    }

    /**
     * 목표 저장
     * 예외 : 날짜가 현재보다 과거이다. 목표 위치와 현재 위치가 10m 이내이다.
     * 예외 발생하지 않으면 DB에 저장
     * @param goalPostDto (날짜, 제목, 목표 위치, 현재 위치)
     */
    @Override
    public GoalDto createGoal(User user, GoalPostDto goalPostDto) {
        LocalDate date = goalPostDto.getDate();

        if (date.compareTo(LocalDate.now()) < 0) {
            throw new PastModifyException("과거 날짜의 목표는 생성할 수 없습니다.");
        }

        Double goalLat = goalPostDto.getArrive_lat();
        Double goalLon = goalPostDto.getArrive_lon();
        Double nowLat = goalPostDto.getStart_lat();
        Double nowLon = goalPostDto.getStart_lon();

        Double distance = distance(goalLat, goalLon, nowLat, nowLon);

        if (distance.compareTo(10.0) <= 0) { // 10m 이내일 시 에러
            throw new TooCloseException("목표로부터 거리가 너무 가깝습니다.");
        }

        Goal goal = goalPostDto.toEntity(user);

        goalRepository.save(goal);

        return modelMapper.map(goal, GoalDto.class);
    }

    // 목표 수정정
   @Override
    public GoalDto updateGoal(GoalPostDto goalPostDto) {
        Goal goal = goalRepository.findById(goalPostDto.getGoalId())
                .orElseThrow(() -> new WrongGoalIDException("잘못된 목표 아이디 입니다."));

        LocalDate date = goalPostDto.getDate();

        if (date.compareTo(LocalDate.now()) < 0) {
            throw new PastModifyException("과거 날짜의 목표는 생성할 수 없습니다.");
        }

        Double goalLat = goalPostDto.getArrive_lat();
        Double goalLon = goalPostDto.getArrive_lon();
        Double nowLat = goalPostDto.getStart_lat();
        Double nowLon = goalPostDto.getStart_lon();

        Double distance = distance(goalLat, goalLon, nowLat, nowLon);

        if (distance.compareTo(10.0) <= 0) {
            throw new TooCloseException("목표로부터 거리가 너무 가깝습니다.");
        }

        goal.update(date, goalLat, goalLon, goalPostDto.getTitle());

        return modelMapper.map(goal, GoalDto.class);
    }

    // 목표 삭제
    @Override
    public void removeGoal(Long goalId){

        Goal goal = goalRepository.findById(goalId)
                .orElseThrow(() -> new WrongGoalIDException("잘못된 목표 아이디입니다."));

        LocalDate date = goal.getDate();

        if (date.compareTo(LocalDate.now()) < 0) {
            throw new PastModifyException("과거 날짜의 목표는 생성할 수 없습니다.");
        }

        goalRepository.delete(goal);
    }

    // 거리 meter 계산
    private static Double distance(Double lat1, Double lon1, Double lat2, Double lon2) {

        Double theta = lon1 - lon2;
        Double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        return dist * 1609.344;

    }

    // This function converts decimal degrees to radians
    private static Double deg2rad(Double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static Double rad2deg(Double rad) {
        return (rad * 180 / Math.PI);
    }
}
