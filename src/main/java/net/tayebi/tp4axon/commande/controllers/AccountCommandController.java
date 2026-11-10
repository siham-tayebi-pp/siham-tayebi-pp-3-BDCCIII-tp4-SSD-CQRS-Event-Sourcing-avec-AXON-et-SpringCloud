package net.tayebi.tp4axon.commande.controllers;

import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.commande.commandes.CreditAccountCommand;
import net.tayebi.tp4axon.commande.commandes.DebitAccountCommand;
import net.tayebi.tp4axon.commande.commandes.UpdateAccountStatusCommand;
import net.tayebi.tp4axon.dto.AddNewAccountRequestDTO;
import net.tayebi.tp4axon.dto.CreditAccountRequestDTO;
import net.tayebi.tp4axon.dto.DebitAccountRequestDTO;
import net.tayebi.tp4axon.dto.UpdateAccountStatusRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/commands/accounts")

public class AccountCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @Autowired
    private EventStore eventStore;

    @PostMapping("/add")
    public CompletableFuture<String> createAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return result;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(Exception exception) {
        Throwable root = exception;
        while (root.getCause() != null) root = root.getCause();
        root.printStackTrace();
        return new ResponseEntity<>(root.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @GetMapping("/events/{accountId}")
    public Stream<?> eventStore(@PathVariable String accountId) {
        return eventStore.readEvents(accountId).asStream();
    }
    @PostMapping("/account/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDTO request) {
        return commandGateway.send(new CreditAccountCommand(
                request.accountId(),
                request.amount(),
                request.currency()
        ));
    }
    @PostMapping("/account/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDTO request) {
        return commandGateway.send(new DebitAccountCommand(
                request.accountId(), request.amount(), request.currency()));
    }
    @PutMapping("/account/updateStatus")
    public CompletableFuture<String> updateStatus(@RequestBody UpdateAccountStatusRequestDTO request) {
        return commandGateway.send(new UpdateAccountStatusCommand(
                request.accountId(), request.status()));
    }

}
