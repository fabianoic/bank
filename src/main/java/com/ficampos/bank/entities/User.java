package com.ficampos.bank.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_user")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true, length = 10)
    private String cpf;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;

    private String phone;



}
