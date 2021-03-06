package com.ficampos.bank.services;

import com.ficampos.bank.dtos.AccountDTO;
import com.ficampos.bank.dtos.UserDTO;
import com.ficampos.bank.entities.User;
import com.ficampos.bank.repositories.UserRepository;
import com.ficampos.bank.services.exceptions.EntityAlreadyExistsException;
import com.ficampos.bank.services.exceptions.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    public UserDTO create(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new EntityAlreadyExistsException("E-mail informado já foi cadastrado!");
        } else if (userRepository.existsByCpf(userDTO.getCpf())) {
            throw new EntityAlreadyExistsException("CPF informado já foi cadastrado!");
        } else if (userRepository.existsByPhone(userDTO.getPhone())) {
            throw new EntityAlreadyExistsException("Telefone informado já foi cadastrado!");
        }

        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .cpf(userDTO.getCpf())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .build();

        user = userRepository.save(user);

        AccountDTO accountDTO = accountService.create(user);

        userDTO = converter(user);

        userDTO.setAccountDTO(accountDTO);

        return userDTO;
    }

    public UserDTO update(UserDTO userDTO) {
        inputValidation(userDTO);

        User user = userRepository.findById(userDTO.getId()).get();

        user.setCpf(userDTO.getCpf());
        user.setEmail(userDTO.getEmail());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setPassword(userDTO.getPassword());
        user.setPhone(userDTO.getPhone());

        user = userRepository.save(user);

        userDTO = converter(user);

        return userDTO;
    }

    public UserDTO findByEmailOrCpf(UserDTO userDTO, String type) {
        User user = null;
        if (type.toUpperCase().equals("CPF")) {
            user = userRepository.findByCpf(userDTO.getCpf());
        } else if (type.toUpperCase().equals("EMAIL")) {
            user = userRepository.findByEmail(userDTO.getEmail());
        }

        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }

        return converter(user);
    }

    public Boolean delete(UserDTO userDTO) {
        inputValidation(userDTO);

        User user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());
        accountService.findAccountByUserToDelete(user);
        userRepository.delete(user);

        return true;
    }

    private UserDTO converter(User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserDTO userDTO = modelMapper.map(user, UserDTO.class);
        return userDTO;
    }

    private void inputValidation(UserDTO userDTO) {
        User user = userRepository.findByEmailAndPassword(userDTO.getEmail(), userDTO.getPassword());

        if (user == null) {
            throw new EntityNotFoundException("Usuário não encontrado");
        }
    }

}
