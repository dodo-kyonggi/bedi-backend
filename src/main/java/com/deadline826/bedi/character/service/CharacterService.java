package com.deadline826.bedi.character.service;

import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.login.Domain.User;

public interface CharacterService {

    CharacterDto setUp(User user);
    CharacterDto getOngoingCharacter(User user);
    CharacterDto reachToNextLevel(User user, Integer point);

}
