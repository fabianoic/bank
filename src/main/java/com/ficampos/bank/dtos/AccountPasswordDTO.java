package com.ficampos.bank.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountPasswordDTO {

    @NotBlank(message = "A conta a ser alterada não pode ser vázio!")
    private AccountDTO accountDTO;
    private Long oldPassword;
    @NotBlank(message = "A nova senha não pode ser vázio!")
    private Long newPassword;

}
