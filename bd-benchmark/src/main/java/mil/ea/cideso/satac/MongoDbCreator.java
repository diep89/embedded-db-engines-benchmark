package mil.ea.cideso.satac;

public class MongoDbCreator extends MotorBD {

    public MongoDbCreator() {
        setEngineName("MongoDb-Embedded");
        setEngineVersion("7.7.67");
    }

    @Override
    public void createNewDatabase(String dbName) {

    }

    @Override
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

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
    public void deleteData(String dbName, String tableName) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

}