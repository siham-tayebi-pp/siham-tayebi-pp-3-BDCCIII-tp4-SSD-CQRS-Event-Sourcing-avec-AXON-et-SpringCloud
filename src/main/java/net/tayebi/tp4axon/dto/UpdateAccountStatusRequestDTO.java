package net.tayebi.tp4axon.dto;

import net.tayebi.tp4axon.enums.AccountStatus;

public record UpdateAccountStatusRequestDTO(String accountId, AccountStatus status) {}
