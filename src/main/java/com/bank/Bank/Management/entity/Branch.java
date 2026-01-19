package com.bank.Bank.Management.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String ifsc;

    private String address;

    @OneToMany(mappedBy = "branch")
    private List<Account> accounts;
}

