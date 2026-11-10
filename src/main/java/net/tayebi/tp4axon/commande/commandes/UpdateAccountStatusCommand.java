package net.tayebi.tp4axon.commande.commandes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.tayebi.tp4axon.enums.AccountStatus;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor

public class UpdateAccountStatusCommand {
    @TargetAggregateIdentifier
    private String id;
    private AccountStatus status;
}
