package com.dodo.bedi.character.repository;

import com.dodo.bedi.character.domain.Characters;
import com.dodo.bedi.character.domain.Collections;
import com.dodo.bedi.login.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collections, Long>, CustomCollectionRepository{

    Collections findOneByUserAndState(User user, String state);
    Collections findOneByUserAndCharacter(User user, Characters characters);

}
