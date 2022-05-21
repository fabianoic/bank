package com.ficampos.bank.controllers;

import com.ficampos.bank.dtos.AccountDTO;
import com.ficampos.bank.dtos.AccountTransferenceDTO;
import com.ficampos.bank.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> deposit(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        AccountDTO sourceAccountResult = accountService.deposit(accountTransferenceDTO.getSource(), accountTransferenceDTO.getDestination(), accountTransferenceDTO.getValue());
        if (sourceAccountResult == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(sourceAccountResult);
    }

    @PostMapping("withdraw")
    public ResponseEntity<AccountDTO> withdraw(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        AccountDTO sourceAccount = accountService.withdraw(accountTransferenceDTO.getSource(), accountTransferenceDTO.getValue());
        if (sourceAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(sourceAccount);
    }

    @PostMapping("pix")
    public ResponseEntity<AccountDTO> pixTransfer(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        AccountDTO sourceAccount = accountService.pixTransference(accountTransferenceDTO.getSource(), accountTransferenceDTO.getPix(), accountTransferenceDTO.getValue());
        if (sourceAccount == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(sourceAccount);
    }
}
