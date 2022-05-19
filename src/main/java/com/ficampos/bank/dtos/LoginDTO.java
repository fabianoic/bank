package com.ficampos.bank.dtos;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class LoginDTO {
    @NotBlank(message = "Por favor, digite um e-mail válido")
    private String email;
    @NotBlank(message = "Por favor, digita sua senha")
    private String password;
}
