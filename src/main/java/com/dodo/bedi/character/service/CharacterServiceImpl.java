package com.dodo.bedi.character.service;

import com.dodo.bedi.character.domain.Characters;
import com.dodo.bedi.character.domain.Collections;
import com.dodo.bedi.character.repository.CharacterRepository;
import com.dodo.bedi.character.repository.CollectionRepository;
import com.dodo.bedi.character.service.dto.CharacterDto;
import com.dodo.bedi.character.service.dto.CollectionDto;
import com.dodo.bedi.login.domain.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            List<Characters> characters = characterRepository.findAllByOrderByLevelAsc();

            Characters zeroLevel = null;

            for (Characters character : characters) {

                Collections init = Collections.builder()
                        .user(user)
                        .character(character)
                        .build();

                if (character.getLevel() == 0) {
                    zeroLevel = character;
                    init.setState("ongoing");
                } else init.setState("incompleted");

                collectionRepository.save(init);
            }

            return modelMapper.map(zeroLevel, CharacterDto.class);
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
    @Transactional(readOnly = true)
    public List<CollectionDto> getCharacterCollection(User user) {
        return collectionRepository.findAllByUserOrderByLevelAsc(user);
    }

    @Override
    @Transactional(readOnly = true)
    public CharacterDto getNextCharacter(User user) {
        Collections ongoing = collectionRepository.findOneByUserAndState(user, "ongoing");

        Characters now = ongoing.getCharacter();

        Characters next = characterRepository.findOneByLevel(now.getLevel() + 1);

        if (next == null) return null;
        else return modelMapper.map(next, CharacterDto.class);
    }

    @Override
    @Transactional
    public CharacterDto reachToNextLevel(User user, Integer point) {

        Collections before = collectionRepository.findOneByUserAndState(user, "ongoing");

        Characters ongoingCharacter = before.getCharacter();

        Characters nextCharacter = characterRepository.findOneByLevel(ongoingCharacter.getLevel() + 1); // 진행 중인 캐릭터의 다음 레벨

        if (nextCharacter == null) return null; // 진행 중인 캐릭터가 최고 레벨

        Integer nextPoint = nextCharacter.getMinimunPointToReach();

        if (point >= nextPoint) {
            Collections next = collectionRepository.findOneByUserAndCharacter(user, nextCharacter);

            before.setState("completed");
            next.setState("ongoing");

            return modelMapper.map(nextCharacter, CharacterDto.class);
        }
        else return null;
    }

}

