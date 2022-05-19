package com.ficampos.bank.controllers.exceptions;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseError implements Serializable {

    private Instant timestamp;
    private String message;
    private Integer status;
}
