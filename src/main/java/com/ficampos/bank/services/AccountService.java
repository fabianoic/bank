package com.ficampos.bank.services;

import com.ficampos.bank.dtos.AccountDTO;
import com.ficampos.bank.dtos.AccountPasswordDTO;
import com.ficampos.bank.dtos.PixDTO;
import com.ficampos.bank.dtos.TransferenceDTO;
import com.ficampos.bank.entities.Account;
import com.ficampos.bank.entities.Pix;
import com.ficampos.bank.entities.User;
import com.ficampos.bank.entities.enumeration.Status;
import com.ficampos.bank.repositories.AccountRepository;
import com.ficampos.bank.repositories.PixRepository;
import com.ficampos.bank.repositories.TransferenceRepository;
import com.ficampos.bank.repositories.UserRepository;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import com.ficampos.bank.services.exceptions.InputInvalidException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Random;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransferenceRepository transferenceRepository;
    @Autowired
    private PixRepository pixRepository;
    @Autowired
    private TransferenceService transferenceService;
    @Value("${spring.agency.value}")
    private Integer agency;

    public AccountDTO create(User user) {
        Account account = Account.builder()
                .agency(agency)
                .accountNumber(new Random().nextInt(99999999))
                .balance(0.0)
                .createdAt(LocalDateTime.now(ZoneId.of("America/Sao_Paulo")))
                .user(user)
                .build();

        account = accountRepository.save(account);

        return mapperByAccount(account);
    }

    public AccountDTO deposit(AccountDTO source, AccountDTO destination, Double value) {
        inputValidation(source, destination, null, value);

        Account accountDestination = accountRepository.findByAgencyAndAccountNumber(destination.getAgency(), destination.getAccountNumber());
        Account accountSource = accountRepository.findByAgencyAndAccountNumber(source.getAgency(), source.getAccountNumber());

        if (!source.equals(destination)) {
            if (accountSource.getBalance() < value) {
                throw new InputInvalidException("O valor ?? acima do saldo existente");
            }
            accountSource.setBalance(accountSource.getBalance() - value);
        }

        TransferenceDTO transferenceDTO =
                transferenceService.create(
                        TransferenceDTO
                                .builder()
                                .value(value)
                                .build()
                        , accountSource, accountDestination);

        accountDestination.setBalance(accountDestination.getBalance() + value);

        accountRepository.save(accountSource);
        accountRepository.save(accountDestination);

        transferenceService.updateStatus(transferenceDTO, Status.COMPLETED);

        return mapperByAccount(accountSource);
    }

    public AccountDTO withdraw(AccountDTO accountDTO, Double value) {
        inputValidation(accountDTO, null, null, value);

        Account account = accountRepository.findByAgencyAndAccountNumber(accountDTO.getAgency(), accountDTO.getAccountNumber());
        if (account.getBalance() - value < 0.0) {
            throw new InputInvalidException("Valor para saque n??o pode ser menor que o saldo disponivel na conta!");
        }

        TransferenceDTO transferenceDTO =
                transferenceService.create(
                        TransferenceDTO
                                .builder()
                                .value(value)
                                .build()
                        , account, account);

        account.setBalance(account.getBalance() - value);

        accountRepository.save(account);

        transferenceService.updateStatus(transferenceDTO, Status.COMPLETED);

        return mapperByAccount(account);

    }

    public AccountDTO pixTransference(AccountDTO source, PixDTO destination, Double value) {
        inputValidation(source, null, destination, value);

        Pix pix = pixRepository.findById_keyAndId_KeyType(destination.getKey(), destination.getType());
        Account accountDestination = pix.getAccount();
        Account accountSource = accountRepository.findByAgencyAndAccountNumber(source.getAgency(), source.getAccountNumber());

        if (!source.equals(destination)) {
            if (accountSource.getBalance() < value) {
                throw new InputInvalidException("O valor a transferir ?? inferior ao saldo existente");
            }
            accountSource.setBalance(accountSource.getBalance() - value);
        }

        TransferenceDTO transferenceDTO =
                transferenceService.create(
                        TransferenceDTO
                                .builder()
                                .value(value)
                                .build()
                        , accountSource, accountDestination);

        accountDestination.setBalance(accountDestination.getBalance() + value);

        accountRepository.save(accountSource);
        accountRepository.save(accountDestination);

        transferenceService.updateStatus(transferenceDTO, Status.COMPLETED);

        return mapperByAccount(accountSource);
    }

    public Boolean findAccountByUserToDelete(User user) {
        Account account = accountRepository.findByUser(user);
        if (account == null) {
            return false;
        }
        accountRepository.delete(account);

        return true;
    }

    public Account findAccountByAgencyAndAccountNumber(AccountDTO accountDTO) {
        inputValidation(accountDTO, null, null, null);

        Account account = accountRepository.findByAgencyAndAccountNumber(accountDTO.getAgency(), accountDTO.getAccountNumber());

        return account;
    }

    public AccountDTO changePassword(AccountPasswordDTO accountPasswordDTO) {
        inputValidation(accountPasswordDTO.getAccountDTO(), null, null, null);

        Account account = accountRepository.findByAgencyAndAccountNumber(accountPasswordDTO.getAccountDTO().getAgency(), accountPasswordDTO.getAccountDTO().getAccountNumber());

        if (account.getPassword() != null) {
            if (accountPasswordDTO.getOldPassword() == null) {
                throw new InputInvalidException("A senha anterior n??o pode ser v??zia!");
            }
            if (!accountPasswordDTO.getOldPassword().equals(account.getPassword())) {
                throw new InputInvalidException("A senha anterior n??o confere!");
            }
            if (account.getPassword().equals(accountPasswordDTO.getNewPassword())) {
                throw new InputInvalidException("A senha antiga n??o pode ser a mesma que a nova!");
            }
            account.setPassword(accountPasswordDTO.getNewPassword());
        } else {
            account.setPassword(accountPasswordDTO.getNewPassword());
        }

        accountRepository.save(account);

        return mapperByAccount(account);
    }

    private AccountDTO mapperByAccount(Account account) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(account, AccountDTO.class);
    }

    private void inputValidation(AccountDTO source, AccountDTO destination, PixDTO destinationPix, Double value) {
        boolean sourceIsNull = source == null,
                destinationIsNull = destination == null,
                destinationPixIsNull = destinationPix == null,
                valueIsNull = value == null;

        if (!sourceIsNull) {
            Account accountSource = accountRepository.findByAgencyAndAccountNumber(source.getAgency(), source.getAccountNumber());
            if (accountSource == null) {
                throw new EntityNotFoundException("A conta remetente n??o foi encontrada");
            }
        }

        if (!destinationPixIsNull) {
            Pix pix = pixRepository.findById_keyAndId_KeyType(destinationPix.getKey(), destinationPix.getType());

            if (pix == null) {
                throw new EntityNotFoundException("A chave pix n??o foi encontrada");
            }
        }

        if (!destinationIsNull) {
            Account accountDestination = accountRepository.findByAgencyAndAccountNumber(destination.getAgency(), destination.getAccountNumber());

            if (accountDestination == null) {
                throw new EntityNotFoundException("A conta de destino n??o foi encontrada");
            }
        }

        if (!valueIsNull) {
            if (value < 0.0) {
                throw new InputInvalidException("Valor para saque n??o pode ser menor que 0");
            }
        }
    }
}
