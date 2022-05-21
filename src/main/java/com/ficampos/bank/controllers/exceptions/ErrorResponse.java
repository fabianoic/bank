package com.ficampos.bank.controllers.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;


@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse implements Serializable {

    private Instant timestamp;
    private String message;
    private Integer status;
}
