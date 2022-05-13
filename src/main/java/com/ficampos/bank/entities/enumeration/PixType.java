package com.ficampos.bank.entities.enumeration;

import lombok.Getter;

@Getter
public enum PixType {
    email("E-mail"), phone("Telefone"), randomKey("Chave Aleatória"), cpf("CPF");

    private String description;

    PixType(String description) {
        this.description = description;
    }
}
