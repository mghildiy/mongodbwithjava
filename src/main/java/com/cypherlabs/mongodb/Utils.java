package com.cypherlabs.mongodb;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import org.bson.Document;

public class Utils {

    public static void printDocuments(FindIterable findIterable){
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        try {
            while (mongoCursor.hasNext()) {
                System.out.println(mongoCursor.next().toJson());
            }
        }finally{
            // close() releases any resources.
            // Closing is only really required if we don't iterate over all the possible results returned by the cursor.
            // Iterating all possible results would close the cursor by itself and all is okay.
            // If a cursor is not closed, db connection corresponding to that cursor is left open and hence unused.
            mongoCursor.close();
        }
    }
}
