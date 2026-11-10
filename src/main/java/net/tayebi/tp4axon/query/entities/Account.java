package net.tayebi.tp4axon.query.entities;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.tp4axon.enums.AccountStatus;

import java.time.Instant;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Account {
    @Id
    private String id;
    private double balance;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    private String currency;
    private Instant createdAt;   // d'abord Date/timestamp, remplacé par Instant
}