package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.Status;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TranferenceDTO {

    private Double value;
    private LocalDateTime createdAt;
    private Status status;
}
