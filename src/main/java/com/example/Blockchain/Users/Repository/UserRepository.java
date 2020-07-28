package com.example.Blockchain.Users.Repository;

import com.example.Blockchain.Users.Entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserEntity, String>{

    UserEntity findById(Long id);

}
