package com.deadline826.bedi.character.domain;

import com.deadline826.bedi.login.Domain.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Collections {

    @Id
    @Column(name="collection_id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "character_id")
    private Characters character;

    @CreatedDate
    private LocalDate createdDate;

    private String state;

    public void setState(String state) {
        this.state = state;
    }

    @Builder
    public Collections(User user, Characters character, String state) {
        this.user = user;
        this.character = character;
        this.state = state;
    }
}
