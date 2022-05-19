package com.ficampos.bank.entities.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PixType {
    EMAIL("E-mail"), PHONE("Telefone"), RANDOMKEY("Chave Aleatória"), CPF("CPF");

    private final String description;

}
