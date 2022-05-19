package com.ficampos.bank.controllers.exceptions;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
public class ResponseError implements Serializable {

    private Instant timestamp;
    private String message;
    private Integer status;
}
