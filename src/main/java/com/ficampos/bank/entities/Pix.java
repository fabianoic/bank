package com.ficampos.bank.entities;

import com.ficampos.bank.entities.enumeration.PixType;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_pix")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pix implements Serializable {
    @EmbeddedId
    @Getter
    private PixPK id;
    @Column(nullable = false, unique = true)
    private String key;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    private void setAccount(Account account) {

        id.setAccount(account);
    }

    private void setPixType(PixType pixType) {

        id.setKeyType(pixType);
    }
}
