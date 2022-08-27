package com.deadline826.bedi.Statistics.Domain;


import com.deadline826.bedi.login.Domain.User;
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
