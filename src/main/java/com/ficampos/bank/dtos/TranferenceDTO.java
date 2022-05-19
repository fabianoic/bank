package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class TranferenceDTO {

    private Double value;
    private LocalDateTime createdAt;
    private Status status;
}
