package com.example.Blockchain.Users.Service;

import com.example.Blockchain.Users.Entities.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UsersService {

    UserEntity createUser(String name, String lastName);

    List<UserEntity> getUsers();

    Boolean deleteUser(Integer id);

}
