package net.tayebi.tp4axon.commande.aggregates;

import lombok.extern.slf4j.Slf4j;
import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.commande.commandes.CreditAccountCommand;
import net.tayebi.tp4axon.commande.events.AccountActivatedEvent;
import net.tayebi.tp4axon.commande.events.AccountCreatedEvent;
import net.tayebi.tp4axon.commande.events.AccountCreditedEvent;
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
//        AggregateLifecycle.apply(new AccountCreatedEvent(
//                command.getId(),
//                command.getInitialBalance(),
//                AccountStatus.CREATED,   // règle métier : un compte créé est à l'état CREATED
//                command.getCurrency()
//        ));
        AggregateLifecycle.apply(new AccountCreatedEvent(
                command.getId(), command.getInitialBalance(), AccountStatus.CREATED, command.getCurrency()));

        AggregateLifecycle.apply(new AccountActivatedEvent(command.getId(), AccountStatus.ACTIVATED));
    }

    // --- Fonction d'évolution ---
    @EventSourcingHandler
    public void on(AccountCreatedEvent event) {
        log.info("AccountCreatedEvent occurred");
        this.accountId = event.getAccountId();
        this.balance = event.getInitialBalance();
        this.status = event.getStatus();
    }

    @CommandHandler
    public void handle(CreditAccountCommand command) {
        log.info("CreditAccountCommand received");
        if (!status.equals(AccountStatus.ACTIVATED))
            throw new RuntimeException("Account " + accountId + " is not activated");
        if (command.getAmount() <= 0)
            throw new IllegalArgumentException("Amount should be positive");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                command.getId(),
                command.getAmount(),
                command.getCurrency()
        ));
    }

//    @EventSourcingHandler
//    public void on(AccountCreditedEvent event) {
//        log.info("AccountCreditedEvent occurred");
//        this.balance += event.getAmount();   // le statut ne change pas
//    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent event) {
        log.info("AccountActivatedEvent occurred");
        this.accountId = event.getAccountId();
        this.status = event.getStatus();
    }
}
