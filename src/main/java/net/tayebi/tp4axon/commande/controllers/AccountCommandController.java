package net.tayebi.tp4axon.commande.controllers;

import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.commande.commandes.CreditAccountCommand;
import net.tayebi.tp4axon.dto.AddNewAccountRequestDTO;
import net.tayebi.tp4axon.dto.CreditAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String handleException(Exception e){
        return e.getMessage();
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

}
