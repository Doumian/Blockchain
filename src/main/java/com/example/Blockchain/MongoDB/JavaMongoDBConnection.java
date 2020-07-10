package com.example.Blockchain.MongoDB;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class JavaMongoDBConnection {

    private static Logger log = Logger.getLogger(JavaMongoDBConnection.class.getName());

    private static MongoClient mongo;

        public static MongoClient getMongo(){
            MongoClientURI uri = new MongoClientURI(
                    "mongodb+srv://dbDavid:141100@Blockchain0.kzhz0.mongodb.net/Blockchain0?retryWrites=true&w=majority");

            MongoClient mongoClient = new MongoClient(uri);
            MongoDatabase database = mongoClient.getDatabase("test");
            //createCollections("Blockchain");
            if (mongo == null) {
                try {
                    mongo = new MongoClient(uri);
                } catch (MongoException ex) {
                    log.log(Level.INFO,ex.toString());
                }
            }
            return mongo;
        }

        // Fetches the mongo database.
        public static MongoDatabase getDB(String db_name) {
            return getMongo().getDatabase(db_name);
        }

        // Fetches the collection from the mongo database.
        public static MongoCollection<Document> getCollection(String db_name, String db_collection) {
            return getDB(db_name).getCollection(db_collection);
        }

        private static void createCollections(String db_name){
            getDB(db_name).createCollection("Users",null);
        }


}