/*
 * package mil.ea.cideso.satac;
 * 
 * import com.mongodb.client.FindIterable; import java.util.Iterator; import
 * com.mongodb.client.MongoCollection; import com.mongodb.client.MongoDatabase;
 * import com.mongodb.MongoClient; import com.mongodb.MongoCredential; import
 * org.bson.Document;
 * 
 * public class MongoDbCreator extends MotorBD {
 * 
 * public MongoDbCreator() { setNombreMotor("MongoDB v3.11.0-beta2"); }
 * 
 * public void createNewDatabase(String dbName) { MongoDatabase database =
 * getDatabase(dbName); }
 * 
 * public void createNewTable(String dbName, String tableName, String[]
 * attributesList, String[] attributesType, String[] attributesLength) {
 * 
 * }
 * 
 * // Collection = Table.
 * 
 * // FUNCIONES AUXILIARES // Creating a Mongo client private MongoDatabase
 * getDatabase(String dbName) { MongoClient mongo = new MongoClient("localhost",
 * 27017);
 * 
 * // Creating Credentials MongoCredential credential; credential =
 * MongoCredential.createCredential("sampleUser", dbName,
 * "password".toCharArray()); //
 * System.out.println("Connected to the database successfully");
 * 
 * // Accessing the database MongoDatabase database = mongo.getDatabase("myDb");
 * return database; }
 * 
 * // Creating a collection database.createCollection("sampleCollection");
 * 
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
 * 
 * @Override public void dropDatabase(String dbName) {
 * 
 * } }
 */