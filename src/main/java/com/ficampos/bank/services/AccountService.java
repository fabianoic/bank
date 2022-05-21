package com.ficampos.bank.services;

import com.ficampos.bank.dtos.AccountDTO;
import com.ficampos.bank.dtos.PixDTO;
import com.ficampos.bank.entities.Account;
import com.ficampos.bank.entities.Pix;
import com.ficampos.bank.entities.Transference;
import com.ficampos.bank.entities.User;
import com.ficampos.bank.entities.enumeration.Status;
import com.ficampos.bank.repositories.AccountRepository;
import com.ficampos.bank.repositories.PixRepository;
import com.ficampos.bank.repositories.TransferenceRepository;
import com.ficampos.bank.repositories.UserRepository;
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
    @Value("${spring.agency.value}")
    private Integer agency;

    public AccountDTO create(User user) {
        Account account = Account.builder()
                .agency(agency)
                .accountNumber(new Random().nextInt(99999999))
                .balance(0.0)
                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                .user(user)
                .build();

        account = accountRepository.save(account);

        ModelMapper modelMapper = new ModelMapper();
        AccountDTO accountDTO = modelMapper.map(account, AccountDTO.class);

        return accountDTO;
    }

    public AccountDTO deposit(AccountDTO source, AccountDTO destination, Double value) {
        Account accountDestination = accountRepository.findByAgencyAndAccountNumber(destination.getAgency(), destination.getAccountNumber());
        Account accountSource = accountRepository.findByAgencyAndAccountNumber(source.getAgency(), source.getAccountNumber());


        System.out.println(accountDestination + "   --   " + accountSource);
        String messageError = "";
        if (accountDestination == null) {
            messageError = "Conta de destina não foi encontrada";
        } else if (accountSource == null) {
            messageError = "A conta remetente não foi encontrada";
        } else if (value < 0.0) {
            messageError = "O valor não pode ser menor que 0";
        }

        if (!messageError.isBlank()) {
            throw new InputInvalidException(messageError);
        }

        if (!source.equals(destination)) {
            if (accountSource.getBalance() < value) {
                throw new InputInvalidException("O valor é acima do saldo existente");
            }
            accountSource.setBalance(accountSource.getBalance() - value);
        }

        //WIP: utilizar serviço de criação de transfer
        Transference transference = Transference.builder()
                .value(value)
                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                .destination(accountDestination)
                .source(accountSource)
                .status(Status.COMPLETED)
                .build();

        accountDestination.setBalance(accountDestination.getBalance() + value);

        accountRepository.save(accountSource);
        accountRepository.save(accountDestination);
        transferenceRepository.save(transference);

        ModelMapper modelMapper = new ModelMapper();
        AccountDTO accountDTO = modelMapper.map(accountSource, AccountDTO.class);

        return accountDTO;
    }

    public AccountDTO withdraw(AccountDTO accountDTO, Double value) {
        Account account = accountRepository.findByAgencyAndAccountNumber(accountDTO.getAgency(), accountDTO.getAccountNumber());

        if (account == null || value < 0.0 || account.getBalance() - value < 0.0) {
            return null;
        }

        //WIP: utilizar serviço de criação de transfer
        Transference transference = Transference.builder()
                .value(value * -1)
                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                .destination(account)
                .source(account)
                .status(Status.COMPLETED)
                .build();

        account.setBalance(account.getBalance() - value);

        accountRepository.save(account);
        transferenceRepository.save(transference);

        ModelMapper modelMapper = new ModelMapper();
        accountDTO = modelMapper.map(account, AccountDTO.class);

        return accountDTO;

    }

    public AccountDTO pixTransference(AccountDTO source, PixDTO destination, Double value) {
        Pix pix = pixRepository.findById_keyAndId_KeyType(destination.getKey(), destination.getType());

        if (pix == null) {
            return null;
        }

        Account accountDestination = pix.getAccount();
        Account accountSource = accountRepository.findByAgencyAndAccountNumber(source.getAgency(), source.getAccountNumber());

        if (accountDestination == null || accountSource == null || value < 0.0) {
            return null;
        }

        if (!source.equals(destination)) {
            if (source.getBalance() < value) {
                return null;
            }
            accountSource.setBalance(accountSource.getBalance() - value);
        }

        //WIP: utilizar serviço de criação de transfer
        Transference transference = Transference.builder()
                .value(value)
                .createdAt(LocalDateTime.now(ZoneId.of("UTC")))
                .destination(accountDestination)
                .source(accountSource)
                .status(Status.COMPLETED)
                .build();

        accountDestination.setBalance(accountDestination.getBalance() + value);

        accountRepository.save(accountSource);
        accountRepository.save(accountDestination);
        transferenceRepository.save(transference);

        ModelMapper modelMapper = new ModelMapper();
        AccountDTO accountDTO = modelMapper.map(accountSource, AccountDTO.class);

        return accountDTO;
    }

    public Boolean findAccountByUserToDelete(User user) {
        Account account = accountRepository.findByUser(user);
        if (account == null) {
            return false;
        }
        accountRepository.delete(account);

        return true;
    }
}
