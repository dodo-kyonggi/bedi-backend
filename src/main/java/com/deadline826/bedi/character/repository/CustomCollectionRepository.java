package com.deadline826.bedi.character.repository;

import com.deadline826.bedi.character.service.dto.CollectionDto;
import com.deadline826.bedi.login.domain.User;

import java.util.List;

public interface CustomCollectionRepository {

    List<CollectionDto> findAllByUserOrderByLevelAsc(User user);

}
