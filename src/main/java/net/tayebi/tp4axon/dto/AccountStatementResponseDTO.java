package net.tayebi.tp4axon.dto;

import net.tayebi.tp4axon.query.entities.Account;
import net.tayebi.tp4axon.query.entities.AccountOperation;

import java.util.List;

public record AccountStatementResponseDTO(Account account, List<AccountOperation> operations) {}
