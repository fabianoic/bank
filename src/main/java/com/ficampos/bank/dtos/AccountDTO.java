package com.ficampos.bank.dtos;

import lombok.*;


@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AccountDTO {

    @EqualsAndHashCode.Include
    private Integer agency;
    @EqualsAndHashCode.Include
    private Integer accountNumber;
    private Long balance;

}
