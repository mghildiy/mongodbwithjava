package com.cypherlabs.mongodb;

import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;

public class MongoDBExp {

    private static final String defaultMongoDBConnectionString = "mongodb://localhost:27017";

    /*public MongoClient getMongoClient(final String connString){
        return StringUtils.isEmpty(connString)? new MongoClient(new MongoClientURI(defaultMongoDBConnectionString)) : new MongoClient(new MongoClientURI(connString));
    }*/

    /*public MongoDatabase getMongoDatabase(final String connString, final String dbName){
        return getMongoClient(connString).getDatabase(dbName);
    }*/

    /*public MongoCollection<Document> getMongoDBCollection(final String connString, final String dbName, final String dbCollection){
        return getMongoDatabase(connString, dbName).getCollection(dbCollection);
    }*/

    public static void main(String[] args){
        List<Integer> books = Arrays.asList(27464, 747854);

        // We interact with mongodb with MongoClient
        // An instance of MongoClient represents a pool of DB connections.
        // We need only one instance of MongoClient, even in multi threaded environment
        // MongoClient mongoClient = MongoClients.create(defaultMongoDBConnectionString);
        MongoClient mongoClient = MongoClients.create(
                MongoClientSettings.builder()
                        .applyToClusterSettings(builder ->
                                builder.hosts(Arrays.asList(new ServerAddress("localhost", 27017))))
                        .build());

        // access database.
        // If a database by input name doesn't exist, mongo would create one when data is stored for first time in that database
        // MongoDatabase instances are immutable
        MongoDatabase database = mongoClient.getDatabase("Examples");

        // Access a collection
        // If a collection does not exist, MongoDB creates the collection when data is stored first time in that collection
        // MongoCollection instances are immutable
        MongoCollection collection = database.getCollection("people");

        /*System.out.println("********************Inserting starts here********************");
        // inserting documents/records
        // insert a single document
        collection.insertOne(new Document("city", "Mumbai")
                .append("address", new BasicDBObject("street", "b7-3, veena nagar, mulund west")
                        .append("state", "MH")
                        .append("zip", 400081)));
        // insert multiple documents
        List<Document> persons = new ArrayList<Document>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Document("city", "Mumbai")
                    .append("address", new BasicDBObject("street", "b7-"+4+i+", veena nagar, mulund west")
                            .append("state", "MH")
                            .append("zip", 4000801+i)));
        }
        collection.insertMany(persons);
        System.out.println("********************Inserting ends here********************");
        */

        // number of documents in collection
        System.out.println("Number of persons:"+collection.countDocuments());

        System.out.println("********************Querying starts here********************");
        // query for documents
        // FindIterable provides an interface to query for documents
        FindIterable personIterable = collection.find();
        // first document
        System.out.println("First person:"+(Document)personIterable.first());
        // all persons
        Utils.printDocuments(personIterable);
        // Although the following idiom for iteration is permissible, avoid its use as the application can leak a cursor if the loop terminates early:
        /*for (Document cur : collection.find()) {
            System.out.println(cur.toJson());
        }*/
        System.out.println("********************Querying ends here********************");

        System.out.println("********************Filter based querying starts here********************");
        // query filter
        // we pass filter object to collection.find()
        personIterable = collection.find(and(gt("address.zip", 4000850), lt("address.zip", 4000877)));
        System.out.println("First person in range:"+(Document)personIterable.first());
        Utils.printDocuments(personIterable);
        System.out.println("********************Filter based querying ends here********************");

        System.out.println("********************Updating starts here********************");
        // update first document
        UpdateResult updateResult = collection.updateOne(eq("address.zip", 4000811), new Document("$set",new Document("address.state", "UK")));
        System.out.println(updateResult.getModifiedCount());
        updateResult = collection.updateMany(and(gte("address.zip", 4000820), lte("address.zip", 4000825)),
                new Document("$set",new Document("address.state", "GJ").append("city","Ahmedabad")));
        System.out.println(updateResult.getModifiedCount());
        System.out.println("********************Updating ends here********************");

        System.out.println("********************Deleting starts here********************");
        DeleteResult deleteResult = collection.deleteMany(gt("address.zip", 4000816));
        System.out.println(deleteResult.getDeletedCount());
        System.out.println("********************Deleting ends here********************");

        // cleanup the resources
        mongoClient.close();
    }
}
