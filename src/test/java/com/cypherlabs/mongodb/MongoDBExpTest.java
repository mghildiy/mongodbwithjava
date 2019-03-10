package com.cypherlabs.mongodb;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class MongoDBExpTest {

    /*@Test
    public void testGetMongoClient(){
        MongoDBExp mongoDBExp = new MongoDBExp();
        MongoClient mongoClient = mongoDBExp.getMongoClient("mongodb://localhost:27017");

        assertNotNull(mongoClient);
    }*/

    /*@Test
    public void testGetMongoDatabase(){
        MongoDBExp mongoDBExp = new MongoDBExp();
        MongoDatabase mongoDB = mongoDBExp.getMongoDatabase("mongodb://localhost:27017", "TestDataBase");

        assertNotNull(mongoDB);
        assertTrue(mongoDB.getName().equals("TestDataBase"));
    }*/

    /*@Test
    public void testGetMongoDBCollection(){
        MongoDBExp mongoDBExp = new MongoDBExp();
        MongoCollection<Document> mongoCollection = mongoDBExp.getMongoDBCollection("mongodb://localhost:27017", "TestDataBase", "TestCollection");

        assertNotNull(mongoCollection);
        assertNotNull(mongoCollection.getNamespace().equals("TestDataBase.TestCollection"));
    }*/
}
