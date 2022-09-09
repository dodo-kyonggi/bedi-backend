package com.deadline826.bedi.character.repository;

import com.deadline826.bedi.character.domain.Characters;
import com.deadline826.bedi.character.domain.Collections;
import com.deadline826.bedi.login.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepository extends JpaRepository<Collections, Long>, CustomCollectionRepository{

    Collections findOneByUserAndState(User user, String state);
    Collections findOneByUserAndCharacter(User user, Characters characters);

}
