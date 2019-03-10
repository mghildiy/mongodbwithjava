package com.cypherlabs.mongodb.pojo;

import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Arrays;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class Test {

    public static void main(String[] args){
        // we create a custom codec registry for POJO-to-BSON translation and viceversa
        CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));

        // set coded registry in MongoClient
        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyToClusterSettings(builder ->
                        builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                .build();
        MongoClient mongoClient = MongoClients.create(settings);

        MongoCollection<Person> collection = mongoClient.getDatabase("Examples").getCollection("people", Person.class);

        System.out.println("Before insertion:"+collection.countDocuments());

        // insert a person
        collection.insertOne(new Person("Albert Einstein", 51, new Address("Andrews Street", "Milan", "M100")));
        collection.insertMany(Arrays.asList(
                new Person("Isaac Newton", 64, new Address("Peters Park", "Oxford", "OX71")),
                new Person("Neils Bohr", 45, new Address("James Park", "Copenhagen", "CH005"))
        ));

        System.out.println("After insertion:"+collection.countDocuments());

        // cleanup the resources
        mongoClient.close();
    }
}
