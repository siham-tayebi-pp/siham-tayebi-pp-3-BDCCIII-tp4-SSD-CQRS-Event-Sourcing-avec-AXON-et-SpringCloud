package net.tayebi.tp4axon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AddNewAccountRequestDTO {
    double initialBalance;
    String currency;

}
