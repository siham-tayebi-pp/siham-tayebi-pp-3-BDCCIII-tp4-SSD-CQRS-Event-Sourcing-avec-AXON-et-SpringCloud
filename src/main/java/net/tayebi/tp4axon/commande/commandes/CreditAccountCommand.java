package net.tayebi.tp4axon.commande.commandes;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Getter
@AllArgsConstructor
public class CreditAccountCommand {
    @TargetAggregateIdentifier
    private String id;
    private double amount;
    private String currency;
}
