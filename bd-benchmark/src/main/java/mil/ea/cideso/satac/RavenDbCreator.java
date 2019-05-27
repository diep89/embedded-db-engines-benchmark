package mil.ea.cideso.satac;

// import net.ravendb.*;

public class RavenDbCreator extends MotorBD {

    int cantidadAInsertar;

    public RavenDbCreator() {
        setEngineName("RavenDB");
        setEngineVersion("4.1.6");
    }

    @Override
    public void createNewDatabase(String dbName) {
        // Start RavenDB Embedded Server with default options
        // EmbeddedServer.INSTANCE.startServer();

        // ServerOptions serverOptions = new ServerOptions();
        // target location of RavenDB data
        // serverOptions.setDataDirectory("C:\\RavenData");
        // serverOptions.setServerUrl("http://127.0.0.1:8080");

        // location where server binaries will be extracted
        // serverOptions.setTargetServerLocation("c:\\RavenServer");
        // EmbeddedServer.INSTANCE.startServer();
        // EmbeddedServer.INSTANCE.getDocumentStore("Embedded");
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