package com.dodo.bedi.character.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterDto {

    private Long id;
    private String name;
    private String img;
    private Integer level;
    private Integer minimunPointToReach;

}
