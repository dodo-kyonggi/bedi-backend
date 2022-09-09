package com.deadline826.bedi.character.repository;

import com.deadline826.bedi.character.domain.Characters;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CharacterRepository extends JpaRepository<Characters, Long> {

    Characters findOneByLevel(Integer level);
    List<Characters> findAllByOrderByLevelAsc();

}
