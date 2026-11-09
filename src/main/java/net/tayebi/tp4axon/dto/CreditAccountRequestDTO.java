package net.tayebi.tp4axon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
public record CreditAccountRequestDTO(
        String accountId, double amount, String currency
) {}

