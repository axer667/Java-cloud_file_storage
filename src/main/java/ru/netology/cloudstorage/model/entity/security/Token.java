package ru.netology.cloudstorage.model.entity.security;

import jakarta.persistence.*;
import lombok.*;
import ru.netology.cloudstorage.model.entity.UserEntity;

import java.io.Serializable;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tokens")
public class Token implements Serializable {

    @Id
    @SequenceGenerator(name = "tokenSequence", sequenceName = "token_sequence", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tokenSequence")
    private Long id;
    @Column(name = "auth_token")
    private String authToken;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    public Token(String authToken, UserEntity userEntity) {
        this.authToken = authToken;
        this.user = userEntity;
    }
}
