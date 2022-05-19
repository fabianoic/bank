package com.ficampos.bank.dtos;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AccountTransferenceDTO {

    private AccountDTO source;
    private AccountDTO destination;
    private PixDTO pix;
    private Double value;
}
