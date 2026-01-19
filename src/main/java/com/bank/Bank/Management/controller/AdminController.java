package com.bank.Bank.Management.controller;

import com.bank.Bank.Management.entity.Branch;
import com.bank.Bank.Management.entity.Role;
import com.bank.Bank.Management.entity.User;
import com.bank.Bank.Management.repository.BranchRepository;
import com.bank.Bank.Management.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BranchRepository branchRepository;
    private final UserRepository userRepository;

    public AdminController(BranchRepository branchRepository,
                           UserRepository userRepository) {
        this.branchRepository = branchRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/branch")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepository.save(branch);
    }

    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    @GetMapping("/customers")
    public List<User> getAllCustomers() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() == Role.CUSTOMER)
                .toList();
    }
}

