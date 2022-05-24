package com.ficampos.bank.controllers;

import com.ficampos.bank.dtos.AccountDTO;
import com.ficampos.bank.dtos.AccountPasswordDTO;
import com.ficampos.bank.dtos.AccountTransferenceDTO;
import com.ficampos.bank.dtos.PixDTO;
import com.ficampos.bank.services.AccountService;
import com.ficampos.bank.services.PixService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private PixService pixService;


    @PostMapping("/deposit")
    public ResponseEntity<AccountDTO> deposit(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        return ResponseEntity.ok(accountService.deposit(accountTransferenceDTO.getSource(), accountTransferenceDTO.getDestination(), accountTransferenceDTO.getValue()));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<AccountDTO> withdraw(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        return ResponseEntity.ok(accountService.withdraw(accountTransferenceDTO.getSource(), accountTransferenceDTO.getValue()));
    }

    @PostMapping("/pix")
    public ResponseEntity<AccountDTO> pixTransfer(@Valid @RequestBody AccountTransferenceDTO accountTransferenceDTO) {
        return ResponseEntity.ok(accountService.pixTransference(accountTransferenceDTO.getSource(), accountTransferenceDTO.getPix(), accountTransferenceDTO.getValue()));
    }

    @PostMapping("/password")
    public ResponseEntity<AccountDTO> changePassword(@Valid @RequestBody AccountPasswordDTO accountPasswordDTO) {
        return ResponseEntity.ok(accountService.changePassword(accountPasswordDTO));
    }

    @PostMapping("/newpix")
    public ResponseEntity<AccountDTO> newPix(@Valid @RequestBody PixDTO pix) {
        PixDTO pixDTO = pixService.create(pix);
        return ResponseEntity.status(HttpStatus.CREATED).body(pixDTO.getAccount());
    }

    @PutMapping("/editpix")
    public ResponseEntity<AccountDTO> editPix(@Valid @RequestBody PixDTO pix) {
        PixDTO pixDTO = pixService.update(pix, pix.getNewKey());
        return ResponseEntity.ok(pixDTO.getAccount());
    }

    @DeleteMapping("/removepix")
    public ResponseEntity<Void> removePix(@Valid @RequestBody PixDTO pix) {
        Boolean response = pixService.delete(pix);
        return ResponseEntity.status(response ? HttpStatus.OK : HttpStatus.BAD_REQUEST).build();
    }
}
