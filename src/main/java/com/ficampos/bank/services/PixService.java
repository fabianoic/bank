package com.ficampos.bank.services;

import com.ficampos.bank.dtos.PixDTO;
import com.ficampos.bank.entities.Account;
import com.ficampos.bank.entities.Pix;
import com.ficampos.bank.repositories.PixRepository;
import com.ficampos.bank.services.exceptions.EntityAlreadyExistsException;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import com.ficampos.bank.services.exceptions.InputInvalidException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PixService {

    @Autowired
    private PixRepository pixRepository;

    @Autowired
    private AccountService accountService;

    public PixDTO create(PixDTO pixDTO) {
        Pix pix = pixRepository.findById_keyAndId_KeyType(pixDTO.getKey(), pixDTO.getType());
        Account account = accountService.findAccountByAgencyAndAccountNumber(pixDTO.getAccount());

        if (pix != null) {
            throw new EntityAlreadyExistsException("O pix informado já existe!");
        } else if (account == null) {
            throw new InputInvalidException("A conta informada não foi encontrada");
        }

        pix = new Pix();
        pix.setKey(pixDTO.getKey());
        pix.setPixType(pixDTO.getType());
        pix.setCreatedAt(LocalDateTime.now());
        pix.setAccount(account);

        pix = pixRepository.save(pix);

        return mapperByPix(pix);
    }

    public PixDTO update(PixDTO pixDTO, String newKey) {
        inputValidation(pixDTO);

        Pix pix = pixRepository.findById_keyAndId_KeyType(pixDTO.getKey(), pixDTO.getType());

        if (pix == null) {
            throw new EntityNotFoundException("Pix informado não encontrada");
        }
        pixRepository.update(pix.getId().getKey(), pix.getId().getKeyType(), newKey, LocalDateTime.now());

        return mapperByPix(pix);
    }

    public Boolean delete(PixDTO pixDTO) {
        inputValidation(pixDTO);

        Pix pix = pixRepository.findById_keyAndId_KeyType(pixDTO.getKey(), pixDTO.getType());

        pixRepository.delete(pix);

        return true;
    }

    private PixDTO mapperByPix(Pix pix) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(pix, PixDTO.class);
    }

    private void inputValidation(PixDTO pixDTO) {
        Pix pix = pixRepository.findById_keyAndId_KeyType(pixDTO.getKey(), pixDTO.getType());

        if (pix == null) {
            throw new EntityNotFoundException("O pix informado não foi encontrado!");
        }
    }
}
