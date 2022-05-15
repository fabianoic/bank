package com.ficampos.bank.entities;

import com.ficampos.bank.entities.enumeration.Status;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_transfer")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transference implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private Double value;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private Status status;

    @OneToOne
    @JoinColumn(name = "source_id", referencedColumnName = "id")
    private Account source;
    @OneToOne
    @JoinColumn(name = "destination_id", referencedColumnName = "id")
    private Account destination;
}
