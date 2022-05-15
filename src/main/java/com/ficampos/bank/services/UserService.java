package com.ficampos.bank.services;

import com.ficampos.bank.dtos.LoginDTO;
import com.ficampos.bank.dtos.UserDTO;
import com.ficampos.bank.entities.User;
import com.ficampos.bank.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountService accountService;

    public UserDTO create(UserDTO userDTO) {
        User user = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .cpf(userDTO.getCpf())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .phone(userDTO.getPhone())
                .build();

        user = userRepository.save(user);

        accountService.create(user);

        ModelMapper modelMapper = new ModelMapper();
        userDTO = modelMapper.map(user, UserDTO.class);

        return userDTO;
    }

}
