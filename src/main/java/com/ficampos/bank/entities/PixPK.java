package com.ficampos.bank.entities;

import com.ficampos.bank.entities.enumeration.PixType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
public class PixPK {
    private Account account;
    private PixType keyType;
}
