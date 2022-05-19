package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.PixType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@Builder
public class PixDTO {

    @NotBlank(message = "O tipo de pix não pode ser vázio!")
    private PixType type;
    @NotBlank(message = "A chave pix não pode ser vázio!")
    private String key;
}
