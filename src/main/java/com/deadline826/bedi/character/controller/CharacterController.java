package com.deadline826.bedi.character.controller;

import com.deadline826.bedi.character.service.CharacterService;
import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.character.service.dto.CollectionDto;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.login.Service.UserService;
import com.deadline826.bedi.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final UserService userService;
    private final PointService pointService;

    @PostMapping("/setup")
    public ResponseEntity<CharacterDto> setup() {
        User user = userService.getUserFromAccessToken();

        CharacterDto characterDto = characterService.setUp(user);

        return ResponseEntity.ok().body(characterDto);
    }

    @GetMapping("/ongoing")
    public ResponseEntity<Map> getUserOngoingCharacter() {

        Map<String, Object> response = new HashMap<>();

        User user = userService.getUserFromAccessToken();

        Integer point = pointService.getAccumulatedPoint(user);
        response.put("point", point);

        CharacterDto ongoing = characterService.getOngoingCharacter(user);
        response.put("character", ongoing);

        CharacterDto next = characterService.getNextCharacter(user);
        if (next != null) response.put("next", next.getMinimunPointToReach());

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/collect")
    public ResponseEntity<List> getUserCollectedCharacters() {

        User user = userService.getUserFromAccessToken();

        List<CollectionDto> collectionDtoList = characterService.getCharacterCollection(user);

        return ResponseEntity.ok().body(collectionDtoList);
    }

}
