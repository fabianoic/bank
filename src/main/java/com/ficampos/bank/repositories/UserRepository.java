package com.ficampos.bank.repositories;

import com.ficampos.bank.entities.User;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    User findByEmailAndPassword(String email, String password);

    User findByEmail(String email);

    User findByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByCpf(String cpf);
}
