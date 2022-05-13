package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.Status;

import java.time.LocalDateTime;

public class TranferDTO {

    private Double value;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Status status;
}
