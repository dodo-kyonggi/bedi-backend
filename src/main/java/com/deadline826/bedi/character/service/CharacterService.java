package com.deadline826.bedi.character.service;

import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.login.Domain.User;
import org.springframework.stereotype.Service;

public interface CharacterService {

    CharacterDto setUp(User user);

}
