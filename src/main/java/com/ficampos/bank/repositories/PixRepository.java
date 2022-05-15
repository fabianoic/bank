package com.ficampos.bank.repositories;

import com.ficampos.bank.entities.Pix;
import com.ficampos.bank.entities.PixPK;
import com.ficampos.bank.entities.enumeration.PixType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PixRepository extends JpaRepository<Pix, PixPK> {

    Pix findById_keyTypeAndKey(PixType pixType, String key);
}
