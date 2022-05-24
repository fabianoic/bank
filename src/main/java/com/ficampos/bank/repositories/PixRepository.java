package com.ficampos.bank.repositories;

import com.ficampos.bank.entities.Pix;
import com.ficampos.bank.entities.PixPK;
import com.ficampos.bank.entities.enumeration.PixType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface PixRepository extends JpaRepository<Pix, PixPK> {

    Pix findById_keyAndId_KeyType(String key, PixType pixType);

    @Modifying
    @Transactional
    @Query(value = "update Pix set updatedAt= :updatedAt, id.key= :newKey where id.key= :oldKey and id.keyType= :pixType")
    void update(@Param("oldKey") String oldKey, @Param("pixType") PixType pixType, @Param("newKey") String newKey, @Param("updatedAt") LocalDateTime updatedAt);
}
