package com.deadline826.bedi.character.repository;

import com.deadline826.bedi.character.domain.Collections;
import com.deadline826.bedi.login.Domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectionRepository extends JpaRepository<Collections, Long> {

    Collections findOneByUserAndState(User user, String state);

}
