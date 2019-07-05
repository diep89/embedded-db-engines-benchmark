package mil.ea.cideso.satac;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;

public class H2Creator extends MotorBD {

    public H2Creator() {
        setEngineName("H2");
        setEngineVersion("1.4.199");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        setUrl("jdbc:h2:./" + dbName);

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
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

        setSql("CREATE TABLE IF NOT EXISTS " + tableName + " (" + "id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT, ");
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

    // Función para operación CREATE
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
    public void readData(String dbName, String tableName) {
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
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

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
    public void deleteData(String dbName, String tableName) {
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
}