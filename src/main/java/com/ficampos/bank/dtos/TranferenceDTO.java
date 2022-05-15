package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.Status;

import java.time.LocalDateTime;

public class TranferenceDTO {

    private Double value;
    private LocalDateTime createdAt;
    private Status status;
}
