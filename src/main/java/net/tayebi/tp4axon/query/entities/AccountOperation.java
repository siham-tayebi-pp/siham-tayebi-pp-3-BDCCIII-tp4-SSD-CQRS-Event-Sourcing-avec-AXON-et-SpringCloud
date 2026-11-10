package net.tayebi.tp4axon.query.entities;

import jakarta.persistence.*;
import lombok.*;
import net.tayebi.tp4axon.enums.AccountStatus;
import net.tayebi.tp4axon.enums.OperationType;

import java.time.Instant;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class AccountOperation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Instant timestamp;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperationType type;
    private String currency;
    @ManyToOne
    private Account account;     // une opération concerne un compte
}