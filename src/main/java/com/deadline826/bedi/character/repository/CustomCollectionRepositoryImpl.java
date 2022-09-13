package com.deadline826.bedi.character.repository;

import com.deadline826.bedi.character.domain.QCharacters;
import com.deadline826.bedi.character.domain.QCollections;
import com.deadline826.bedi.character.service.dto.CollectionDto;
import com.deadline826.bedi.login.domain.QUser;
import com.deadline826.bedi.login.domain.User;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CustomCollectionRepositoryImpl implements CustomCollectionRepository {

    @Autowired
    JPAQueryFactory query;
    QCollections qCollections = QCollections.collections;
    QCharacters qCharacters = QCharacters.characters;
    QUser qUser = QUser.user;


    @Override
    public List<CollectionDto> findAllByUserOrderByLevelAsc(User user) {
        return query
                .select(Projections.constructor(CollectionDto.class,
                        qCharacters.name.as("characterName"),
                        qCharacters.img.as("characterImage"),
                        qCharacters.level.as("characterLevel"),
                        qCollections.state))
                .from(qCollections)
                .innerJoin(qCollections.character, qCharacters)
                .innerJoin(qCollections.user, qUser)
                .where(qUser.eq(user))
                .fetch();
    }

}
