package com.deadline826.bedi.character.service;

import com.deadline826.bedi.character.domain.Characters;
import com.deadline826.bedi.character.domain.Collections;
import com.deadline826.bedi.character.repository.CharacterRepository;
import com.deadline826.bedi.character.repository.CollectionRepository;
import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.login.Domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CharacterServiceImpl implements CharacterService {

    private final CharacterRepository characterRepository;
    private final CollectionRepository collectionRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public CharacterDto setUp(User user) {

        Collections collections = collectionRepository.findOneByUserAndState(user, "ongoing");

        if (collections == null) {
            Characters characters = characterRepository.findOneByLevel(0);

            Collections ongoing = Collections.builder()
                    .user(user)
                    .character(characters)
                    .state("ongoing")
                    .build();

            collectionRepository.save(ongoing);

            return modelMapper.map(characters, CharacterDto.class);
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterDto getOngoingCharacter(User user) {

        Collections collections = collectionRepository.findOneByUserAndState(user, "ongoing");

        Characters characters = collections.getCharacter();

        return modelMapper.map(characters, CharacterDto.class);
    }

    @Override
    @Transactional
    public CharacterDto reachToNextLevel(User user, Integer point) {

        Collections collections = collectionRepository.findOneByUserAndState(user, "ongoing");

        Characters ongoing = collections.getCharacter();

        Characters next = characterRepository.findOneByLevel(ongoing.getLevel() + 1);

        if (next == null) return null;

        Integer nextPoint = next.getMinimunPointToReach();

        if (point >= nextPoint) {
            setNextLevel(user, collections, next);
            return modelMapper.map(next, CharacterDto.class);
        }
        else return null;
    }

    private void setNextLevel(User user, Collections before, Characters next) {
        before.setState("completed");

        Collections collections = Collections.builder()
                .user(user)
                .character(next)
                .state("ongoing")
                .build();

        collectionRepository.save(collections);
    }

}

