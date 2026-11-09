package net.tayebi.tp4axon.commande.controllers;

import net.tayebi.tp4axon.commande.commandes.AddAccountCommand;
import net.tayebi.tp4axon.dto.AddNewAccountRequestDTO;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/commands/accounts")

public class AccountCommandController {
    @Autowired
    private CommandGateway commandGateway;
    @PostMapping("/add")
    public String addNewAccount(@RequestBody AddNewAccountRequestDTO request) {
        commandGateway.send(AddAccountCommand addAccounCommand);

    }
}
