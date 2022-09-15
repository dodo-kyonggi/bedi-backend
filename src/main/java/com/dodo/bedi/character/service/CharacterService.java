package com.dodo.bedi.character.service;

import com.dodo.bedi.character.service.dto.CharacterDto;
import com.dodo.bedi.character.service.dto.CollectionDto;
import com.dodo.bedi.login.domain.User;

import java.util.List;

public interface CharacterService {

    CharacterDto setUp(User user);
    CharacterDto getOngoingCharacter(User user);
    CharacterDto getNextCharacter(User user);
    List<CollectionDto> getCharacterCollection(User user);
    CharacterDto reachToNextLevel(User user, Integer point);

}
