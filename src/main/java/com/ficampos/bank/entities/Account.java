package com.ficampos.bank.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_account")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false, length = 4)
    private Integer agency;
    @Column(nullable = false, length = 8)
    private Long accountNumber;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private LocalDateTime updatedAt;
    @Column(nullable = false)
    private Long password;
    private Double balance;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private Set<Pix> keys;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;



}
