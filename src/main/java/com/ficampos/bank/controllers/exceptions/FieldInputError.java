package com.ficampos.bank.controllers.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FieldInputError {

    private String name;
    private String message;

}
