package com.deadline826.bedi.character.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Characters {

    @Id
    @GeneratedValue
    @Column(name="character_id")
    private Long id;

    private String name;
    private String img;
    private Integer level;
    private Integer minimunPointToReach;

    @Builder
    public Characters(String name, String img, Integer level, Integer minimunPointToReach) {
        this.name = name;
        this.img = img;
        this.level = level;
        this.minimunPointToReach = minimunPointToReach;
    }

}
