package mil.ea.cideso.satac;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.MongoCredential;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MongoDbCreator extends MotorBD {

    MongoClient mongoClient;

    int cantidadAInsertar;

    public MongoDbCreator() {
        setEngineName("MongoDB");
        setEngineVersion("3.10.1");
    }

    // Creación de BD.
    // Si la BD no existe, es generada automáticamente.
    @Override
    public void createNewDatabase(String dbName) {
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        db.createCollection("sampleCollection"); // Genero una colección dummy para que se genere la BD.
        System.out.println("La BD se ha generado correctamente.\n");
        mongoClient.close();
    }

    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        db.createCollection(tableName);
        System.out.println("La colección se ha generado correctamente.");
        System.out.println("BD: " + dbName + ".db\nColección: " + tableName + "\n");
        mongoClient.close();

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.

        setAttributesQty(attributesList.length);
        setAttributesList(attributesList);
        setAttributesType(attributesType);
        setAttributesLength(attributesLength);
    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;

        getTimer().start();
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        MongoCollection<Document> collection = db.getCollection(tableName);
        List<Document> documents = new ArrayList<Document>();

        // new String(getAttributesList()[j] + i);

        for (int i = 0; i < cantidadAInsertar; i++) {
            Document doc = new Document();
            for (int j = 0; j < getAttributesQty(); j++) {
                if (getAttributesList()[j] == "int") {
                    doc.append(getAttributesList()[j], i);
                } else {
                    doc.append(getAttributesList()[j], "M");
                }
            }

            documents.add(doc);

            getTimer().stop();

            if (i + 1 < cantidadAInsertar) {
                System.out.print((i + 1) + " - ");
            } else {
                System.out.print((i + 1) + ".");
            }

            getTimer().start();

        }

        collection.insertMany(documents);

        mongoClient.close();
        getTimer().stop();

        System.out.println("");
        System.out.println("");

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void readData(String dbName, String tableName) {
        getTimer().start();
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        MongoCollection<Document> collection = db.getCollection(tableName);

        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        Iterator<Document> it = iterDoc.iterator();
        getTimer().stop();

        System.out.println("Registros leídos:\n");

        while (it.hasNext()) {
            System.out.println("Documento " + i + ":\n");
            System.out.println(it.next() + "\n");
            i++;
        }

        getTimer().start();
        mongoClient.close();
        getTimer().stop();

        System.out.println("");

        setStatsReadOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        getTimer().start();
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        MongoCollection<Document> collection = db.getCollection(tableName);

        for (int i = 0; i < getAttributesQty(); i++) {
            if (getAttributesType()[i] == "int") {
                collection.updateMany(Filters.eq(getAttributesList()[i]), Updates.set(getAttributesList()[i], 10));
            } else {
                collection.updateMany(Filters.eq(getAttributesList()[i]), Updates.set(getAttributesList()[i], "F"));
            }
        }
        getTimer().stop();

        // Si quisiera hacer un update de varios campos simultáneamente:
        // collection.updateMany(Filters.eq("stars", 2),
        // Updates.combine(Updates.set("stars", 0),
        // Updates.currentDate("lastModified")));

        FindIterable<Document> iterDoc = collection.find();
        int i = 1;
        Iterator<Document> it = iterDoc.iterator();

        System.out.println("Registros actualizados correctamente.");
        System.out.println("Lista de registros actualizada: \n");

        while (it.hasNext()) {
            System.out.println("Documento " + i + ":\n");
            System.out.println(it.next() + "\n");
            i++;
        }

        getTimer().start();
        mongoClient.close();
        getTimer().stop();

        setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void deleteData(String dbName, String tableName) {
        getTimer().start();
        mongoClient = getClient();
        MongoDatabase db = getDatabase(dbName, mongoClient);
        MongoCollection<Document> collection = db.getCollection(tableName);
        collection.drop();
        mongoClient.close();
        getTimer().stop();

        setStatsDeleteOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void dropDatabase(String dbName) {

    }

    // En MongoDB | En SQL
    // Collection = Table.
    // Documento = Fila

    // FUNCIONES AUXILIARES
    public MongoClient getClient() {
        // Creating a Mongo client private MongoDatabase
        // You can instantiate a MongoClient object without any parameters to connect to
        // a MongoDB instance running on localhost on port 27017:
        MongoClient mongoClient = MongoClients.create();
        return mongoClient;
    }

    public MongoDatabase getDatabase(String dbName, MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return database;
    }
}