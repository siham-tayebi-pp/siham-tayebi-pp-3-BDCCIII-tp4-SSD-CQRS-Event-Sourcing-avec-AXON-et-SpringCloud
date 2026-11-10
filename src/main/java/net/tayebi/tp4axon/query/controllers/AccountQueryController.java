package net.tayebi.tp4axon.query.controllers;

import net.tayebi.tp4axon.dto.AccountStatementResponseDTO;
import net.tayebi.tp4axon.query.entities.Account;
import net.tayebi.tp4axon.query.entities.AccountOperation;
import net.tayebi.tp4axon.query.queries.GetAccountStatementQuery;
import net.tayebi.tp4axon.query.queries.GetAllAccountsQuery;
import net.tayebi.tp4axon.query.queries.WatchEventQuery;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.queryhandling.SubscriptionQueryResult;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/query/account")
public class AccountQueryController {

    private QueryGateway queryGateway;

    public AccountQueryController(QueryGateway queryGateway) {
        this.queryGateway = queryGateway;
    }

    @GetMapping("/all")
    public CompletableFuture<List<Account>> getAllAccounts() {
        return queryGateway.query(new GetAllAccountsQuery(),
                ResponseTypes.multipleInstancesOf(Account.class));
    }
    @GetMapping("/accountStatement/{accountId}")
    public CompletableFuture<AccountStatementResponseDTO> getAccountStatement(@PathVariable String accountId) {
        return queryGateway.query(new GetAccountStatementQuery(accountId),
                ResponseTypes.instanceOf(AccountStatementResponseDTO.class));
    }
    @GetMapping(value = "/watch/{accountId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<AccountOperation> watch(@PathVariable String accountId) {
        SubscriptionQueryResult<AccountOperation, AccountOperation> result =
                queryGateway.subscriptionQuery(
                        new WatchEventQuery(accountId),
                        ResponseTypes.instanceOf(AccountOperation.class),   // résultat initial
                        ResponseTypes.instanceOf(AccountOperation.class));  // mises à jour
        return result.initialResult().concatWith(result.updates());
    }
}