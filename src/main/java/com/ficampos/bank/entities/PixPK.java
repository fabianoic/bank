package com.ficampos.bank.entities;

import com.ficampos.bank.entities.enumeration.PixType;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class PixPK implements Serializable {

    @Column(nullable = false, unique = true)
    private String key;
    @Column(nullable = false)
    private PixType keyType;
}
