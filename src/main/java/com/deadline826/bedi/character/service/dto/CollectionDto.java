package com.deadline826.bedi.character.service.dto;

import com.deadline826.bedi.character.domain.Characters;

public class CollectionDto {

    private String characterName;
    private String characterImage;
    private Integer characterLevel;
    private String state;

    public CollectionDto(Characters characters, String state) {
        this.characterName = characters.getName();
        this.characterImage = characters.getImg();
        this.characterLevel = characters.getLevel();
        this.state = state;
    }

}
