package net.tayebi.tp4axon.commande.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tayebi.tp4axon.enums.AccountStatus;

@Getter
@AllArgsConstructor
public class AccountCreatedEvent {
    private String accountId;
    private double initialBalance;
    private AccountStatus status;
    private String currency;
}
