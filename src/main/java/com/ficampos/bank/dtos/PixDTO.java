package com.ficampos.bank.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ficampos.bank.entities.enumeration.PixType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PixDTO {

    @NotNull(message = "O tipo de pix não pode ser vázio!")
    private PixType type;
    @NotBlank(message = "A chave pix não pode ser vázio!")
    private String key;
    @NotNull(message = "A conta associada ao pix não pode ser vázio!")
    private AccountDTO account;
    private String newKey;
}
