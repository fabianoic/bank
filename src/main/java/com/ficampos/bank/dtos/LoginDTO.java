package com.ficampos.bank.dtos;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Por favor, digite um e-mail válido")
    private String email;
    @NotBlank(message = "Por favor, digita sua senha")
    private String password;
}
