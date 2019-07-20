package mil.ea.cideso.satac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.DatabaseMetaData;

public class SqliteCreator extends MotorBD {

    private String dbName;
    private String tableName;
    private int cantidadAInsertar;

    private String url;
    private String sql;

    // Atributos para conexión SQL
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public SqliteCreator() {
        setEngineName("SQLite");
        setEngineVersion("3.27.2.1");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);
        // String url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
        setUrl("jdbc:sqlite:" + getDbName() + ".db");

        try {
            getConnection(getUrl());
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha generado correctamente.\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        finally {
            try {
                if (getConn() != null)
                    getConn().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }

    }

    // Función para crear una nueva tabla en la BD.
    @Override
    public void createNewTable(String tableName) {

        setTableName(tableName);

        setSql("CREATE TABLE IF NOT EXISTS " + getTableName() + " ("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");

        for (int i = 0; i < getAttributesQty() - 1; i++) {
            setSql(getSql().concat(
                    getAttributesList()[i] + " " + getAttributesType()[i] + "(" + getAttributesLength()[i] + "), "));
        }
        // En la siguiente línea, se debe concatenar al string 'sql' la última
        // ocurrencia de la lista de atributos.
        // Para ello utilizo 'attributesList.length', pero hay que restarle 1.
        // (El método length la cantidad de elementos contando a partir de '1',
        // pero el string 'sql' inicia en '0')
        setSql(getSql()
                .concat(getAttributesList()[getAttributesQty() - 1] + " " + getAttributesType()[getAttributesQty() - 1]
                        + "(" + getAttributesLength()[getAttributesQty() - 1] + "));"));

        try {
            getConnection(getUrl());
            setStmt(getConn().createStatement());
            getStmt().execute(getSql());
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La tabla se ha generado correctamente.");
                System.out.println("BD: " + dbName + ".db\nTabla: " + tableName + "\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        finally {
            try {
                if (getStmt() != null)
                    getStmt().close();
                if (getConn() != null)
                    getConn().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }

    }

    // Función para operación CREATE
    @Override
    public void insertData(int cantidadAInsertar) {

        setCantidadAInsertar(cantidadAInsertar);

        // Lógica para la generación de la sentencia SQL de inserción de datos.
        setSql("INSERT INTO " + getTableName() + " (");
        for (int k = 0; k < getAttributesQty() - 1; k++) {
            setSql(getSql().concat(getAttributesList()[k] + ", "));
        }
        setSql(getSql().concat(getAttributesList()[getAttributesQty() - 1] + ") VALUES ("));
        for (int k = 0; k < getAttributesQty() - 1; k++) {
            setSql(getSql().concat("?, "));
        }
        setSql(getSql().concat("?);"));

        // tiempoInicio = System.currentTimeMillis();
        for (int j = 0; j < cantidadAInsertar; j++) {
            try {
                getTimer().start();
                getConnection(getUrl());
                setPstmt(getConn().prepareStatement(getSql()));
                getPstmt().setInt(1, j);
                getPstmt().setString(2, "M");
                getPstmt().setInt(3, j);
                getPstmt().executeUpdate();
                getTimer().stop();
                if (getConn() != null) {
                    // DatabaseMetaData meta = conn.getMetaData();
                    // System.out.println("The driver name is " + meta.getDriverName());
                    // System.out.println("A new record (" + (j + 1) + ") has been inserted.");
                    // System.out.println("");
                    if (j + 1 < cantidadAInsertar) {
                        System.out.print((j + 1) + " - ");
                    } else {
                        System.out.print((j + 1) + ".");
                    }
                }
            }

            catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }

            finally {
                try {
                    if (getPstmt() != null)
                        getPstmt().close();
                    if (getConn() != null)
                        getConn().close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }

        System.out.println("");
        System.out.println("");

        // Forma de tomar el tiempo alternativa.
        // tiempoFin = System.currentTimeMillis();
        // tiempoTest = tiempoFin - tiempoInicio;
        // (tiempoTest / 1000)

        setStatsCreateOperation(getTimer().toString());

        // Reseteo el timer.
        setTimer(getTimer().reset());

    }

    // Función para operación READ
    @Override
    public void readData() {
        setSql("SELECT * FROM " + tableName);

        try {
            getTimer().start();
            getConnection(getUrl());
            setStmt(getConn().createStatement());
            setRs(getStmt().executeQuery(getSql()));
            getTimer().stop();

            // Recorro los resultados y los muestro por pantalla
            int i = 1;
            System.out.println("Registros leídos:\n");
            System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");
            while (getRs().next()) {
                System.out.printf("%-10d %-10s %-10s %-10s\n", i, getRs().getInt("edad"), getRs().getString("sexo"),
                        getRs().getInt("telefono"));
                i++;
            }
            System.out.println("");
            setStatsReadOperation(getTimer().toString()); // Guardo el valor del timer
            setTimer(getTimer().reset()); // Reseteo el timer.
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (getRs() != null)
                    getRs().close();
                if (getStmt() != null)
                    getStmt().close();
                if (getConn() != null)
                    getConn().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }
    }

    // Función para operación UPDATE
    @Override
    public void updateData() {

        // Lógica para la generación de la sentencia SQL de inserción de datos.
        setSql("UPDATE " + tableName + " SET ");

        for (int k = 0; k < getAttributesQty() - 1; k++) {
            setSql(getSql().concat(getAttributesList()[k] + " = ? , "));
        }
        setSql(getSql().concat(getAttributesList()[getAttributesQty() - 1] + " = ? WHERE sexo = ?"));

        try {
            getTimer().start();
            getConnection(getUrl());
            setPstmt(getConn().prepareStatement(getSql()));
            getPstmt().setInt(1, 10);
            getPstmt().setString(2, "F");
            getPstmt().setInt(3, 10);
            getPstmt().setString(4, "M");
            getPstmt().executeUpdate();
            getTimer().stop();

            if (getConn() != null) {
                setSql("SELECT * FROM " + tableName);
                try {
                    getConnection(getUrl());
                    setStmt(getConn().createStatement());
                    setRs(getStmt().executeQuery(getSql()));

                    // Recorro los resultados y los muestro por pantalla
                    int i = 1;
                    System.out.println("Registros actualizados correctamente.");
                    System.out.println("Lista de registros actualizada: \n");
                    System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");
                    while (getRs().next()) {
                        System.out.printf("%-10d %-10s %-10s %-10s\n", i, getRs().getInt("edad"),
                                getRs().getString("sexo"), getRs().getInt("telefono"));
                        i++;
                    }
                    System.out.println("");
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }

            }
        }

        catch (SQLException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.exit(1);
        }

        finally {
            try {
                if (getPstmt() != null)
                    getPstmt().close();
                if (getConn() != null)
                    getConn().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }

        setStatsUpdateOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void deleteData() {
        setSql("DELETE FROM " + tableName + " WHERE sexo = ? ");

        try {
            getTimer().start();
            getConnection(getUrl());
            setPstmt(getConn().prepareStatement(getSql()));
            getPstmt().setString(1, "F");
            getPstmt().executeUpdate();
            getTimer().stop();
            if (getConn() != null) {
                System.out.println("Registros eliminados correctamente.\n");
            }
        }

        catch (SQLException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.exit(1);
        }

        finally {
            try {
                if (getPstmt() != null)
                    getPstmt().close();
                if (getConn() != null)
                    getConn().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }

        setStatsDeleteOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función (opcional) para eliminar la BD generada
    @Override
    public void dropDatabase() {
        setSql("DROP DATABASE " + dbName + ".db");

        try {
            getConnection(getUrl());
            getStmt().execute(getSql());
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha eliminado correctamente.\n");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("");
        }

        finally {
            try {
                if (getConn() != null)
                    getConn().close();
                if (getStmt() != null)
                    getStmt().close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            }
        }

    }

    // Función para generar la conexión a la BD
    public Connection getConnection(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Statement getStmt() {
        return stmt;
    }

    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    public PreparedStatement getPstmt() {
        return pstmt;
    }

    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
    }

    public ResultSet getRs() {
        return rs;
    }

    public void setRs(ResultSet rs) {
        this.rs = rs;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        if (url == null) {
            url = new String();
        }
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the sql
     */
    public String getSql() {
        if (sql == null) {
            sql = new String();
        }
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Connection getConn() {
        return conn;
    }

    // Funciones para configurar test manual

    /*
     * public void createNewAttribute(String dbName, String tableName, String
     * AttributeName, String attributeType, Integer attributeSize) {
     * 
     * for (String i : url) { try { if (AttributeName.equalsIgnoreCase("varchar")) {
     * sql = "ALTER TABLE " + tableName + " ADD " + AttributeName + " " +
     * attributeType + "(" + attributeSize + ")"; } else { sql = "ALTER TABLE " +
     * tableName + " ADD " + AttributeName + " " + attributeType; } conn =
     * connect(i); setStmt(conn.createStatement()); getStmt().execute(sql); if (conn
     * != null) { DatabaseMetaData meta = conn.getMetaData();
     * System.out.println("The driver name is " + meta.getDriverName());
     * System.out.println("A new attribute has been created."); if
     * (attributeType.equalsIgnoreCase("varchar")) { System.out.println("Database: "
     * + dbName + "\nTable: " + tableName + "\nAttribute: " + AttributeName + " (" +
     * attributeType.toUpperCase() + "(" + attributeSize + "))\n"); } else {
     * System.out.println("Database: " + dbName + "\nTable: " + tableName +
     * "\nAttribute: " + AttributeName + " (" + attributeType.toUpperCase() +
     * ")\n"); } } }
     * 
     * catch (SQLException e) { System.out.println("Error.");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * }
     * 
     * finally { try { if (getStmt() != null) getStmt().close(); if (conn != null)
     * conn.close(); } catch (SQLException e) { System.out.println("Error.");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * } } } }
     * 
     * public void insertText(String dbName, String tableName, String attributeName,
     * String attributeText) {
     * 
     * for (String i : url) { try { sql = "INSERT INTO " + tableName + "(" +
     * attributeName + ") VALUES (?)"; conn = connect(i);
     * setPstmt(conn.prepareStatement(sql)); getPstmt().setString(1, attributeText);
     * getPstmt().executeUpdate(); if (conn != null) { DatabaseMetaData meta =
     * conn.getMetaData(); System.out.println("The driver name is " +
     * meta.getDriverName());
     * System.out.println("A new attribute has been entered.");
     * System.out.println("Database: " + dbName + "\nTable: " + tableName +
     * "\nAttribute: " + attributeName + "\nAttribute Value: " + attributeText +
     * "\n"); } } catch (SQLException e) {
     * System.out.println("Error en base de datos");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * } finally { try { if (getPstmt() != null) getPstmt().close(); if (conn !=
     * null) conn.close(); } catch (SQLException e) { System.out.println("Error.");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * } } } }
     * 
     * public void insertNumber(String dbName, String tableName, String
     * attributeName, Integer attributeValue) {
     * 
     * for (String i : url) { try { sql = "INSERT INTO " + tableName + "(" +
     * attributeName + ") VALUES (?)"; conn = connect(i);
     * setPstmt(conn.prepareStatement(sql)); getPstmt().setInt(1, attributeValue);
     * getPstmt().executeUpdate(); if (conn != null) { DatabaseMetaData meta =
     * conn.getMetaData(); System.out.println("The driver name is " +
     * meta.getDriverName());
     * System.out.println("A new attribute has been entered.");
     * System.out.println("Database: " + dbName + "\nTable: " + tableName +
     * "\nAttribute: " + attributeName + "\nAttribute Value: " + attributeValue +
     * "\n"); } } catch (SQLException e) { System.out.println("Error.");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * } finally { try { if (getPstmt() != null) getPstmt().close(); if (conn !=
     * null) conn.close(); } catch (SQLException e) { System.out.println("Error.");
     * System.out.println("Detalle del error: \n" + e.getMessage()); System.exit(1);
     * } } } }
     */
}
