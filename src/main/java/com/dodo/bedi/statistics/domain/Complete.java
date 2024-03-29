package com.dodo.bedi.statistics.domain;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
public class Complete {

    @Id
    @Column(name="user_id")
    private Long id;


    //@Column(name="complete_count")
    private Long completeCount;

}
