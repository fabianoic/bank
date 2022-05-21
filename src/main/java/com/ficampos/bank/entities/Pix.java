package com.ficampos.bank.entities;

import com.ficampos.bank.entities.enumeration.PixType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_pix")
public class Pix implements Serializable {
    @EmbeddedId
    @Getter
    private PixPK id;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private void setKey(String key) {

        id.setKey(key);
    }

    private void setPixType(PixType pixType) {

        id.setKeyType(pixType);
    }
}
