package com.deadline826.bedi.character.service;

import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.character.service.dto.CollectionDto;
import com.deadline826.bedi.login.Domain.User;

import java.util.List;

public interface CharacterService {

    CharacterDto setUp(User user);
    CharacterDto getOngoingCharacter(User user);
//    List<CollectionDto> getCharacterCollection(User user);
    CharacterDto reachToNextLevel(User user, Integer point);

}