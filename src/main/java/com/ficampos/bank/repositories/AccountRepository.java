package com.ficampos.bank.repositories;

import com.ficampos.bank.entities.Account;
import com.ficampos.bank.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Account findByAgencyAndAccountNumber(Integer agency, Integer accountNumber);

    Account findByUser(User user);
}
