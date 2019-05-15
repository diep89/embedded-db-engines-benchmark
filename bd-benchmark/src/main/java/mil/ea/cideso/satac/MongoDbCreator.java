package mil.ea.cideso.satac;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.MongoCredential;
import org.bson.Document;

import java.util.Iterator;

public class MongoDbCreator extends MotorBD {

    public MongoDbCreator() {
        setEngineName("MongoDB");
        setEngineVersion("3.10.1");
    }

    // Creación de BD.
    // Si la BD no existe, es generada automáticamente.
    @Override
    public void createNewDatabase(String dbName) {
        MongoDatabase database = getDatabase(dbName);
        // Inserción dummy para que la BD se genere.
        database.createCollection("sampleCollection");
        // Document buildInfoResults = database.runCommand(new Document("buildInfo",
        // 1));
        // System.out.println(buildInfoResults.toJson());
        // System.out.println("Driver: " + meta.getDriverName());
        System.out.println("La BD se ha generado correctamente.\n");

    }

    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        MongoDatabase database = getDatabase(dbName);
        database.createCollection(tableName);
        database.getName();
    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {

    }

    @Override
    public void readData(String dbName, String tableName) {

    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

    // En MongoDB:
    // Collection = Table.

    // FUNCIONES AUXILIARES
    public MongoDatabase getDatabase(String dbName) {
        // Creating a Mongo client private MongoDatabase
        // You can instantiate a MongoClient object without any parameters to connect to
        // a MongoDB instance running on localhost on port 27017:
        MongoClient mongoClient = MongoClients.create();

        // Creating Credentials
        MongoCredential credential = MongoCredential.createCredential("admin", dbName, "admin".toCharArray());

        // Accessing the database
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return database;
    }
    /*
     * // Retrieving a collection MongoCollection<Document> collection =
     * database.getCollection("myCollection");
     * 
     * // Insertion Document document = new Document("title",
     * "MongoDB").append("id", 1).append("description", "database") .append("likes",
     * 100);collection.insertOne(document);
     * 
     * // Retrieving all documents // Getting the iterable object
     * FindIterable<Document> iterDoc = collection.find(); int i = 1;
     * 
     * // Getting the iterator Iterator<Document> it = iterDoc.iterator();
     * 
     * while(it.hasNext()) { System.out.println(it.next()); i++; }
     */

}