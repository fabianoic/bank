package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.PixPK;
import com.ficampos.bank.entities.enumeration.PixType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PixDTO {

    @NotBlank(message = "O tipo de pix não pode ser vázio!")
    private PixType type;
    @NotBlank(message = "A chave pix não pode ser vázio!")
    private String key;
}
