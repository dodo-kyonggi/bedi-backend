package com.dodo.bedi.statistics.repository;


//import com.querydsl.sql.SQLExpressions;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;


@Transactional
@RequiredArgsConstructor
public class CompleteRepositoryImpl implements CustomCompleteRepository {

    private final JdbcTemplate jdbcTemplate;

    String SQL = "select *, RANK() OVER( ORDER BY complete_count DESC) as ranking from complete ";



    @Override
    public String getRank(Long userId,Double numberOfUser){
        String ranking = String.valueOf(numberOfUser);

        List<Map<String, Object>> userList = jdbcTemplate.queryForList(SQL);
        for (Map<String, Object> userInfo : userList) {

            if ( String.valueOf(userInfo.get("user_id")).equals( String.valueOf(userId) ) ){
                ranking = String.valueOf(userInfo.get("ranking") );
                break;
            }

        }
        return ranking;

    }



}