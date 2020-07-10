package com.example.Blockchain.Services;

import com.example.Blockchain.Entities.BlockEntity;
import com.example.Blockchain.Entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public interface BlockChainUsersService {

    UserEntity createUser(String name, String lastName);

    List<UserEntity> getUsers();

    Boolean deleteUser(Integer id);

}
