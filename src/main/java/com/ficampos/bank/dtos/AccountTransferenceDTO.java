package com.ficampos.bank.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransferenceDTO {

    private AccountDTO source;
    private AccountDTO destination;
    private PixDTO pix;
    private Double value;
}
