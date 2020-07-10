package com.example.Blockchain.Services.Impl;

import com.example.Blockchain.Entities.UserEntity;
import com.example.Blockchain.MongoDB.JavaMongoDBConnection;
import com.example.Blockchain.Services.BlockChainUsersService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BlockChainUsersServiceImpl implements BlockChainUsersService {

    JavaMongoDBConnection connection = new JavaMongoDBConnection();

    @Override
    public UserEntity createUser(String name, String lastName) {
        UserEntity user = new UserEntity(name, lastName);
        MongoCollection<Document> collection = JavaMongoDBConnection.getCollection("Blockchain","Users");
        Document document = new Document();
        document.put(name, lastName);
        collection.insertOne(document);
        return user;
    }

    @Override
    public List<UserEntity> getUsers() {
        return null;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        return null;
    }



}
