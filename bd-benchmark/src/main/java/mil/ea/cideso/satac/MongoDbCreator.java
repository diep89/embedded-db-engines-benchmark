package mil.ea.cideso.satac;

import com.mongodb.client.FindIterable;
import java.util.Iterator;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import org.bson.Document;

public class MongoDbCreator {
    // Creating a Mongo client
    MongoClient mongo = new MongoClient("localhost", 27017);

    // Creating Credentials
    MongoCredential credential;credential=MongoCredential.createCredential("sampleUser","myDb","password".toCharArray());System.out.println("Connected to the database successfully");

    // Accessing the database
    MongoDatabase database = mongo.getDatabase("myDb");
    // System.out.println("Credentials ::" + credential);

    // Creating a collection
    database.createCollection("sampleCollection");

    // Retrieving a collection
    MongoCollection<Document> collection = database.getCollection("myCollection");

    // Insertion
    Document document = new Document("title", "MongoDB").append("id", 1).append("description", "database")
            .append("likes", 100);collection.insertOne(document);

    // Retrieving all documents
    // Getting the iterable object
    FindIterable<Document> iterDoc = collection.find();
    int i = 1;

    // Getting the iterator
    Iterator<Document> it = iterDoc.iterator();

    while(it.hasNext())
    {
        System.out.println(it.next());
        i++;
    }
}