package net.tayebi.tp4axon.query.handlers;

import net.tayebi.tp4axon.dto.AccountStatementResponseDTO;
import net.tayebi.tp4axon.query.entities.Account;
import net.tayebi.tp4axon.query.entities.AccountOperation;
import net.tayebi.tp4axon.query.queries.GetAccountStatementQuery;
import net.tayebi.tp4axon.query.queries.GetAllAccountsQuery;
import net.tayebi.tp4axon.query.queries.WatchEventQuery;
import net.tayebi.tp4axon.query.repository.AccountOperationRepository;
import net.tayebi.tp4axon.query.repository.AccountRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AccountQueryHandler {

    private AccountRepository accountRepository;
    private AccountOperationRepository operationRepository;

    public AccountQueryHandler(AccountRepository accountRepository,
                               AccountOperationRepository operationRepository) {
        this.accountRepository = accountRepository;
        this.operationRepository = operationRepository;
    }

    @QueryHandler
    public List<Account> on(GetAllAccountsQuery query) {
        return accountRepository.findAll();
    }
    @QueryHandler
    public AccountStatementResponseDTO on(GetAccountStatementQuery query) {
        Account account = accountRepository.findById(query.getAccountId()).get();
        List<AccountOperation> operations = operationRepository.findByAccountId(query.getAccountId());
        return new AccountStatementResponseDTO(account, operations);
    }
    @QueryHandler
    public AccountOperation on(WatchEventQuery query) {
        return AccountOperation.builder().build();
    }
}
