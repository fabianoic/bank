package com.ficampos.bank.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {

    private Integer agency;
    private Long accountNumber;
    private Long balance;
}
