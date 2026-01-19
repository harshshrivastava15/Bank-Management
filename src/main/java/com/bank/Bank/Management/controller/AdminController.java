package com.bank.Bank.Management.controller;

import com.bank.Bank.Management.entity.Branch;
import com.bank.Bank.Management.repository.BranchRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final BranchRepository branchRepository;

    public AdminController(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @PostMapping("/branch")
    public Branch createBranch(@RequestBody Branch branch) {
        return branchRepository.save(branch);
    }
}

