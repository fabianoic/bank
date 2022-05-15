package com.ficampos.bank.repositories;

import com.ficampos.bank.entities.Transference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransferenceRepository extends JpaRepository<Transference, UUID> {
}
