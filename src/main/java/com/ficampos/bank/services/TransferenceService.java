package com.ficampos.bank.services;

import com.ficampos.bank.dtos.TransferenceDTO;
import com.ficampos.bank.entities.Account;
import com.ficampos.bank.entities.Transference;
import com.ficampos.bank.entities.enumeration.Status;
import com.ficampos.bank.repositories.TransferenceRepository;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import com.ficampos.bank.services.exceptions.InputInvalidException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TransferenceService {

    @Autowired
    private TransferenceRepository transferenceRepository;

    @Autowired
    private AccountService accountService;

    public TransferenceDTO create(TransferenceDTO transferenceDTO) {
        Account source = accountService.findAccountByAgencyAndAccountNumber(transferenceDTO.getSource());
        Account destination = null;
        if (transferenceDTO.getDestination() != null) {
            destination = accountService.findAccountByAgencyAndAccountNumber(transferenceDTO.getDestination());
        } else if (transferenceDTO.getDestinationPix() != null) {
            destination = accountService.findAccountByAgencyAndAccountNumber(transferenceDTO.getDestinationPix().getAccountDTO());
        } else {
            throw new InputInvalidException("Conta de destino não foi encontrada!");
        }

        Transference transference = new Transference();
        transference.setValue(transferenceDTO.getValue());
        transference.setStatus(Status.SENDING);
        transference.setSource(source);
        transference.setDestination(destination);
        transference.setCreatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        transference = transferenceRepository.save(transference);

        return mapperByTransference(transference);
    }

    public TransferenceDTO updateStatus(TransferenceDTO transferenceDTO, Status status) {
        Transference transference = transferenceRepository.findById(transferenceDTO.getId()).orElseThrow(() -> new EntityNotFoundException("Transferência não existente!"));

        transference.setStatus(status);
        transference.setUpdatedAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")));

        transference = transferenceRepository.save(transference);

        return mapperByTransference(transference);
    }


    private TransferenceDTO mapperByTransference(Transference transference) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(transference, TransferenceDTO.class);
    }
}
