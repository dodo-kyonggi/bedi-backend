package com.deadline826.bedi.character.service;

import com.deadline826.bedi.character.domain.Characters;
import com.deadline826.bedi.character.domain.Collections;
import com.deadline826.bedi.character.repository.CharacterRepository;
import com.deadline826.bedi.character.repository.CollectionRepository;
import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.character.service.dto.CollectionDto;
import com.deadline826.bedi.login.Domain.User;
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
        List<CollectionDto> collectionDtoList = collectionRepository.findAllByUserOrderByLevelAsc(user);
        System.out.println("collectionDtoList.size() = " + collectionDtoList.size());
        return collectionDtoList;
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

            return modelMapper.map(next, CharacterDto.class);
        }
        else return null;
    }

}

