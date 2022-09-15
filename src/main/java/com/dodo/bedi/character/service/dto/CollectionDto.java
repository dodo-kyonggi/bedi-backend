package com.dodo.bedi.character.service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionDto {

    private String characterName;
    private String characterImage;
    private Integer characerLevel;
    private String state;

    public CollectionDto(String characterName, String characterImage, Integer characerLevel, String state) {
        this.characterName = characterName;
        this.characterImage = characterImage;
        this.characerLevel = characerLevel;
        this.state = state;
    }

}
