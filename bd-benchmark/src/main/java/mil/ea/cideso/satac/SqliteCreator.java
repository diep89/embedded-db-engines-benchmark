package mil.ea.cideso.satac;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class SqliteCreator extends MotorBD {

    public SqliteCreator() {
        setEngineName("SQLite");
        setEngineVersion("3.27.2.1");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        // String url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
        setUrl("jdbc:sqlite:" + dbName + ".db");

        try {
            // Descomentar para eliminar la BD.
            // setSql("DROP DATABASE IF EXISTS " + dbName + ";");
            // setStmt(getConn().createStatement());
            // getStmt().execute(getSql());

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
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

        setSql("CREATE TABLE IF NOT EXISTS " + tableName + " (" + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ");
        setAttributesList(attributesList);
        setAttributesType(attributesType);
        setAttributesLength(attributesLength);
        setAttributesQty(getAttributesList().length);

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

    // Función para operaciones CREATE
    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {

        // Lógica para la generación de la sentencia SQL de inserción de datos.
        setSql("INSERT INTO " + tableName + " (");
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
                    System.out.print((j + 1) + " - ");
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

    // Función (opcional) para eliminar la BD generada
    @Override
    public void dropDatabase(String dbName) {
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
