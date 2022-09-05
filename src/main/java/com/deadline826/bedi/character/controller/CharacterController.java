package com.deadline826.bedi.character.controller;

import com.deadline826.bedi.character.service.CharacterService;
import com.deadline826.bedi.character.service.dto.CharacterDto;
import com.deadline826.bedi.login.Domain.User;
import com.deadline826.bedi.login.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/character")
@RequiredArgsConstructor
public class CharacterController {

    private final CharacterService characterService;
    private final UserService userService;

    @PostMapping("/setup")
    public ResponseEntity<CharacterDto> setup() {
        User user = userService.getUserFromAccessToken();

        CharacterDto characterDto = characterService.setUp(user);

        return ResponseEntity.ok().body(characterDto);
    }

}
