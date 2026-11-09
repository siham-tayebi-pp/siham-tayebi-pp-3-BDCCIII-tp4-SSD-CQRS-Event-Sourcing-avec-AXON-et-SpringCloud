package net.tayebi.tp4axon.commande.aggregates;

import lombok.extern.slf4j.Slf4j;
import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.commande.events.AccountCreatedEvent;
import net.tayebi.tp4axon.enums.AccountStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class AccountAggregate {

    @AggregateIdentifier
    private String accountId;
    private double balance;
    private AccountStatus status;

    // Constructeur sans paramètre : obligatoire pour Axon, on n'y touche pas
    public AccountAggregate() {}

    // --- Fonction de décision ---
    @CommandHandler
    public AccountAggregate(AddAccountCommand command) {
        log.info("AddAccountCommand received");
        // Règle métier
        if (command.getInitialBalance() <= 0)
            throw new IllegalArgumentException("Balance must be positive");
        // Émission de l'événement
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(),
                command.getInitialBalance(),
                AccountStatus.CREATED,   // règle métier : un compte créé est à l'état CREATED
                command.getCurrency()
        ));
    }

    // --- Fonction d'évolution ---
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("AccountCreatedEvent occurred");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }
}
