package net.tayebi.tp4axon.commande.controllers;

import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.dto.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/commands/accounts")

public class AccountCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @PostMapping("/add")
    public CompletableFuture<String> createAccount(@RequestBody AddNewAccountRequestDTO request){
        CompletableFuture<String> result = this.commandGateway.send(new AddAccountCommand(
                UUID.randomUUID().toString(),
                request.getInitialBalance(),
                request.getCurrency()
        ));
        return result;
    }
//    @ExceptionHandler(Exception.class)
//    public String handleException(Exception e){
//        return e.getMessage();
//    }

}
