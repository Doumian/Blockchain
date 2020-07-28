package com.example.Blockchain.Users.Service.Impl;

import com.example.Blockchain.Users.Service.UsersService;
import com.example.Blockchain.Users.Entities.UserEntity;
import com.example.Blockchain.MongoDB.JavaMongoDBConnection;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.mongodb.client.model.Indexes.ascending;
import static java.util.stream.IntStream.iterate;

@Service
public class UsersServiceImpl implements UsersService {

    private static final AtomicInteger count = new AtomicInteger(getLastCustomId());

    @Override
    public UserEntity createUser(String name, String lastName) {
        UserEntity user = new UserEntity(name, lastName);
        MongoCollection<Document> collection = JavaMongoDBConnection.getCollection("Blockchain","Users");
        Document document = new Document();
        document.put("id",count.incrementAndGet());
        document.put("name", name);
        document.put("lastname", lastName);
        collection.insertOne(document);
        return user;
    }

    @Override
    public List<UserEntity> getUsers() {
        MongoCollection<Document> collection = JavaMongoDBConnection.getCollection("Blockchain","Users");
        List<UserEntity> dbUsers = new ArrayList<>();
        printData(collection);
        FindIterable<Document> iterable = collection.find(); // (1)
        MongoCursor<Document> cursor = iterable.iterator(); // (2)

        try {
            cursor.forEachRemaining(document -> {
                String name = document.get("name").toString();
                String lastname = document.get("lastname").toString();
                dbUsers.add(new UserEntity(name,lastname));
            });
        } finally {
            cursor.close();
        }
        return dbUsers;
    }

    @Override
    public Boolean deleteUser(Integer id) {
        return null;
    }

    private void printData(MongoCollection<Document> coll) {


        FindIterable<Document> iterable = coll.find(); // (1)

        MongoCursor<Document> cursor = iterable.iterator(); // (2)
        try {
            while(cursor.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }

    }

    private static Integer getLastCustomId(){

        MongoCollection<Document> collection = JavaMongoDBConnection.getCollection("Blockchain","Users");
        FindIterable<Document> iterable = collection.find().sort(ascending("id")).limit(1);
        MongoCursor<Document> cursor = iterable.iterator();
        return cursor.next().getInteger("id");
        //Not working
    }


}
