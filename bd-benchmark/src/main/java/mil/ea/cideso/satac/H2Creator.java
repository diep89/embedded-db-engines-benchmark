package mil.ea.cideso.satac;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class H2Creator extends MotorBD {

    private String tableName;

    private String url;
    private String sql;

    // Atributos para conexión SQL
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public H2Creator() {
        setEngineName("H2");
        setEngineVersion("1.4.199");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);

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

    // Función para operación CREATE
    @Override
    public void insertData(int cantidadAInsertar) {

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
        setSql("DROP DATABASE " + getDbName() + ".db");

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

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public Connection getConnection(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
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
}
