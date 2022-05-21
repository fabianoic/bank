package com.ficampos.bank.dtos;

import com.ficampos.bank.entities.enumeration.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferenceDTO {

    private UUID id;
    private Double value;
    private LocalDateTime createdAt;
    private Status status;

    private AccountDTO source;
    private AccountDTO destination;
    private PixDTO destinationPix;
}
