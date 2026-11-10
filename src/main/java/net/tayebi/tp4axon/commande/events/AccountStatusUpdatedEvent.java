package net.tayebi.tp4axon.commande.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.tayebi.tp4axon.enums.AccountStatus;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AccountStatusUpdatedEvent {
    private String accountId;
    private AccountStatus status;
}
