package net.tayebi.tp4axon.query.handlers;

import lombok.extern.slf4j.Slf4j;
import net.tayebi.tp4axon.commande.events.*;
import net.tayebi.tp4axon.enums.OperationType;
import net.tayebi.tp4axon.query.entities.Account;
import net.tayebi.tp4axon.query.entities.AccountOperation;
import net.tayebi.tp4axon.query.repository.AccountOperationRepository;
import net.tayebi.tp4axon.query.repository.AccountRepository;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AccountEventHandler {

    private AccountRepository accountRepository;
    private AccountOperationRepository operationRepository;

    public AccountEventHandler(AccountRepository accountRepository,
                               AccountOperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage eventMessage) {
        log.info("Query side : AccountCreatedEvent received");
        Account account = Account.builder()
                .id(event.getAccountId())
                .balance(event.getInitialBalance())
                .status(event.getStatus())
                .createdAt(eventMessage.getTimestamp())
                .currency(event.getCurrency())          // oublié au début, corrigé ensuite
                .build();
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountActivatedEvent event) {
        log.info("Query side : AccountActivatedEvent received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountStatusUpdatedEvent event) {
        log.info("Query side : AccountStatusUpdatedEvent received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        account.setStatus(event.getStatus());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountDebitedEvent event, EventMessage eventMessage) {
        log.info("Query side : AccountDebitedEvent received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .timestamp(eventMessage.getTimestamp())
                .type(OperationType.DEBIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        // Mise à jour du solde
        account.setBalance(account.getBalance() - accountOperation.getAmount());
        accountRepository.save(account);
    }

    @EventHandler
    public void on(AccountCreditedEvent event, EventMessage eventMessage) {
        log.info("Query side : AccountCreditedEvent received");
        Account account = accountRepository.findById(event.getAccountId()).get();
        AccountOperation accountOperation = AccountOperation.builder()
                .amount(event.getAmount())
                .timestamp(eventMessage.getTimestamp())
                .type(OperationType.CREDIT)
                .currency(event.getCurrency())
                .account(account)
                .build();
        operationRepository.save(accountOperation);
        // Mise à jour du solde
        account.setBalance(account.getBalance() + accountOperation.getAmount());
        accountRepository.save(account);
    }
}
