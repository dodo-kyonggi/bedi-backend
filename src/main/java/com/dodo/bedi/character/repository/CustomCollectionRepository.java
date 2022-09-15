package com.dodo.bedi.character.repository;

import com.dodo.bedi.character.service.dto.CollectionDto;
import com.dodo.bedi.login.domain.User;

import java.util.List;

public interface CustomCollectionRepository {

    List<CollectionDto> findAllByUserOrderByLevelAsc(User user);

}
