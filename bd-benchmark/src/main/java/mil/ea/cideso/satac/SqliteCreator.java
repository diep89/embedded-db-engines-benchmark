package mil.ea.cideso.satac;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Persistence;

public class SqliteCreator extends MotorBD {
    // JDBC driver name and database URL
    private final String jdbcDriver = "org.sqlite.JDBC";
    private String dbUrl;

    // Database credentials
    private final String user = "";
    private final String pass = "";

    private int cantidadAInsertar;

    public SqliteCreator() {
        setEngineName("SQLite");
        setEngineVersion("3.28.0");
        setProviderName("Hibernate JPA");
        setProviderVersion("5.4.3");
    }

    // Función para operación CREATE
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);
        setDbUrl("jdbc:sqlite:" + getDbName() + ".db");

        getTimer().start();
        initializeJdbcConnection(getJdbcDriver(), getUser(), getPass(), getDbUrl());
        getTimer().stop();

        try {
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha generado correctamente.\n\n");
            }
        } catch (SQLException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        }

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación INSERT
    // El método 'generarAmenazaWrapper()' genera todos los objetos que
    // componen una
    // amenaza, con todos sus atributos inicializados y todas sus relaciones
    // declaradas, y devuelve todos los objetos AmenazaWrapper en una lista.
    // Luego se realiza la persistencia mediante ciclo 'while'.
    // El tiempo de generación de los objetos de prueba se excluye de las mediciones
    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);
        setEmf(Persistence.createEntityManagerFactory("SQLitePersistence"));

        List<AmenazaWrapper> testObjectsList = generateTestObjects(getCantidadAInsertar());

        getTimer().start();
        persistTestObjects(testObjectsList);
        getTimer().stop();

        System.out.println("Registros persistidos correctamente.\n\n");

        setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación READ
    @Override
    public void readData() {
        getTimer().start();
        readTestObjects();
        getTimer().stop();

        System.out.println("Registros leídos correctamente.\n\n");

        setStatsReadOperation(getTimer().toString()); // Guardo el valor del timer
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación UPDATE
    @Override
    public void updateData() {
        getTimer().start();
        updateTestObjects();
        getTimer().stop();

        System.out.println("Registros actualizados correctamente.\n\n");

        setStatsUpdateOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación DELETE
    @Override
    public void deleteData() {
        getTimer().start();
        deleteTestObjects();
        getTimer().stop();

        System.out.println("Registros eliminados correctamente.\n\n");

        setStatsDeleteOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función (opcional) para eliminar la BD generada
    @Override
    public void dropDatabase() {
        // setSql("DROP DATABASE " + getDbName() + ".db");

        // try {
        // getConnection(getUrl());
        // getStmt().execute(getSql());
        // if (getConn() != null) {
        // DatabaseMetaData meta = getConn().getMetaData();
        // System.out.println("Driver: " + meta.getDriverName());
        // System.out.println("La BD se ha eliminado correctamente.\n");
        // }

        // } catch (SQLException e) {
        // System.out.println(e.getMessage());
        // System.out.println("");
        // }

        // finally {
        // try {
        // if (getConn() != null)
        // getConn().close();
        // if (getStmt() != null)
        // getStmt().close();
        // } catch (SQLException e) {
        // System.out.println("Error.");
        // System.out.println("Detalle del error: \n" + e.getMessage());
        // System.exit(1);
        // }
        // }

    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public void setDbUrl(String dbUrl) {
        this.dbUrl = dbUrl;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }

}
