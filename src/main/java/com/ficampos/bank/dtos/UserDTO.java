package com.ficampos.bank.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private UUID id;
    @NotBlank(message = "O nome não pode ser vázio!")
    private String firstName;
    @NotBlank(message = "O sobrenome não pode ser vázio!")
    private String lastName;
    @CPF(message = "O CPF não pode ser vázio!")
    private String cpf;
    @Email(message = "O E-mail não pode ser vázio!")
    private String email;
    @NotBlank(message = "A senha não poder ser vázia!")
    private String password;

    private String phone;

    private AccountDTO accountDTO;
}
