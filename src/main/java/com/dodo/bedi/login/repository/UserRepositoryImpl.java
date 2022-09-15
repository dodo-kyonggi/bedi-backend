package com.dodo.bedi.login.repository;

import com.dodo.bedi.login.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@RequiredArgsConstructor
public class UserRepositoryImpl implements CustomUserRepository{

    private final JPAQueryFactory queryfactory;
    private final QUser user = QUser.user;


    @Override
    public List<Long> getUserCount() {
        return queryfactory.select(user.count())
                .from(user)
                .fetch();
    }
}
