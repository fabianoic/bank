package com.ficampos.bank.services.exceptions;

public class EntityAlreadyExistsException extends RuntimeException {

    public EntityAlreadyExistsException(String msg) {
        super(msg);
    }

}
