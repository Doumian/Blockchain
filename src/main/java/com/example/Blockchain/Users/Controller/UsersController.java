package com.example.Blockchain.Users.Controller;

import com.example.Blockchain.Users.Entities.UserEntity;
import com.example.Blockchain.Users.Repository.UserRepository;
import com.example.Blockchain.Users.Service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blockchain/users")
public class UsersController {

    @Autowired
    UsersService usersService;

    @Autowired
    UserRepository repository;

    @PostMapping(value = "/newUser")
    public UserEntity
    generateNewBlock(@RequestParam String name, @RequestParam String lastName){
        return this.usersService.createUser(name, lastName);
    }

    @GetMapping(value = "/{id}")
    UserEntity getUser(@PathVariable Long id) {
        return repository.findById(id);
    }

    @GetMapping(value = "/size")
    public List<UserEntity> getUsers(){
        return this.usersService.getUsers();
    }

    @DeleteMapping
    public Boolean deleteUser(Integer id){
        return this.usersService.deleteUser(id);
    }
}

