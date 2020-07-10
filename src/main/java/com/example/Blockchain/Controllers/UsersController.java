package com.example.Blockchain.Controllers;

import com.example.Blockchain.DTOs.UsersTransactionWrapperDTO;
import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Entities.UserEntity;
import com.example.Blockchain.Services.BlockChainService;
import com.example.Blockchain.Services.BlockChainUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/blockchain/users")
public class UsersController {

    @Autowired
    BlockChainUsersService blockChainUsersService;

    @PostMapping(value = "/newUser")
    public UserEntity
    generateNewBlock(@RequestParam String name, @RequestParam String lastName){
        return this.blockChainUsersService.createUser(name, lastName);
    }

    @GetMapping(value = "/size")
    public List<UserEntity> getUsers(){
        return this.blockChainUsersService.getUsers();
    }

    @DeleteMapping
    public Boolean deleteUser(Integer id){
        return this.blockChainUsersService.deleteUser(id);
    }
}

