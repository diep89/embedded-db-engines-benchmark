package mil.ea.cideso.satac;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.google.common.base.Stopwatch;

public class SqliteCreator {
    ArrayList<String> url = new ArrayList<String>();
    String sql;
    String[] attributesList;
    String[] attributesType;
    String[] attributesLength;

    private Connection conn = null;
    private Statement stmt;
    private PreparedStatement pstmt;

    // private Long tiempoInicio, tiempoFin, tiempoTest;
    Stopwatch timer = Stopwatch.createUnstarted();

    static private String timerReturned;

    static String getTimer() {
        return timerReturned;
    }

    // Función para generar la conexión a la BD
    private Connection connect(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // Funcion para crear una nueva BD.
    public void createNewDatabase(String dbName) {
        // String url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
        url.add("jdbc:sqlite:" + dbName);
        // url.add("");
        // url.add("");
        // url.add("");

        for (String i : url) {
            try {
                conn = connect(i);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("Driver: " + meta.getDriverName());
                    System.out.println("La BD se ha generado correctamente.\n");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            finally {
                try {
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    // Función para crear una nueva tabla en la BD.
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " ("
                + "id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, ";
        this.attributesList = attributesList;
        this.attributesType = attributesType;
        this.attributesLength = attributesLength;

        for (int i = 0; i < attributesList.length - 1; i++) {
            sql = sql.concat(attributesList[i] + " " + attributesType[i] + "(" + attributesLength[i] + "), ");
        }
        // En la siguiente línea, se debe concatenar al string 'sql' la última
        // ocurrencia de la lista de atributos.
        // Para ello utilizo 'attributesList.length', pero hay que restarle 1.
        // (El método length la cantidad de elementos contando a partir de '1',
        // pero el string 'sql' inicia en '0')
        sql = sql.concat(attributesList[attributesList.length - 1] + " " + attributesType[attributesList.length - 1]
                + "(" + attributesLength[attributesList.length - 1] + "));");

        for (String i : url) {
            try {
                conn = connect(i);
                stmt = conn.createStatement();
                stmt.execute(sql);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("Driver: " + meta.getDriverName());
                    System.out.println("La tabla se ha generado correctamente.");
                    System.out.println("BD: " + dbName + "\nTabla: " + tableName + "\n");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            finally {
                try {
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    // Funciones para configurar test automatizado

    public void insertData(String dbName, String tableName, int cantidadAInsertar) {

        // Lógica para la generación de la sentencia SQL de inserción de datos.
        sql = "INSERT INTO " + tableName + " (";
        for (int k = 0; k < attributesList.length - 1; k++) {
            sql = sql.concat(attributesList[k] + ", ");
        }
        sql = sql.concat(attributesList[attributesList.length - 1] + ") VALUES (");
        for (int k = 0; k < attributesList.length - 1; k++) {
            sql = sql.concat("?, ");
        }
        sql = sql.concat("?);");

        for (String i : url) {
            // tiempoInicio = System.currentTimeMillis();
            for (int j = 0; j < cantidadAInsertar; j++) {
                try {
                    timer.start();
                    conn = connect(i);
                    pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, j);
                    pstmt.setString(2, "M");
                    pstmt.setInt(3, 11111111);
                    pstmt.executeUpdate();
                    timer.stop();
                    if (conn != null) {
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
                        if (pstmt != null)
                            pstmt.close();
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        System.out.println("Error.");
                        System.out.println("Detalle del error: \n" + e.getMessage());
                        System.exit(1);
                    }
                }
            }

            System.out.println("");
            System.out.println("");

            // tiempoFin = System.currentTimeMillis();
            // tiempoTest = tiempoFin - tiempoInicio;
            // (tiempoTest / 1000)

            // System.out.println("");
            // System.out.printf("El tiempo de ejecución fue de " + timer + ".\n");
            // System.out.println("");

            timerReturned = timer.toString();

            timer.reset();
        }
    }

    public void dropDatabase(String dbName) {
        sql = "DROP DATABASE " + dbName;

        for (String i : url) {
            try {
                conn = connect(i);
                stmt.execute(sql);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("Driver: " + meta.getDriverName());
                    System.out.println("La BD se ha eliminado correctamente.\n");
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("");
            }

            finally {
                try {
                    if (conn != null)
                        conn.close();
                    if (stmt != null)
                        stmt.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    // Funciones para configurar test manual

    public void createNewAttribute(String dbName, String tableName, String AttributeName, String attributeType,
            Integer attributeSize) {

        for (String i : url) {
            try {
                if (AttributeName.equalsIgnoreCase("varchar")) {
                    sql = "ALTER TABLE " + tableName + " ADD " + AttributeName + " " + attributeType + "("
                            + attributeSize + ")";
                } else {
                    sql = "ALTER TABLE " + tableName + " ADD " + AttributeName + " " + attributeType;
                }
                conn = connect(i);
                stmt = conn.createStatement();
                stmt.execute(sql);
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new attribute has been created.");
                    if (attributeType.equalsIgnoreCase("varchar")) {
                        System.out.println("Database: " + dbName + "\nTable: " + tableName + "\nAttribute: "
                                + AttributeName + " (" + attributeType.toUpperCase() + "(" + attributeSize + "))\n");
                    } else {
                        System.out.println("Database: " + dbName + "\nTable: " + tableName + "\nAttribute: "
                                + AttributeName + " (" + attributeType.toUpperCase() + ")\n");
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
                    if (stmt != null)
                        stmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    public void insertText(String dbName, String tableName, String attributeName, String attributeText) {

        for (String i : url) {
            try {
                sql = "INSERT INTO " + tableName + "(" + attributeName + ") VALUES (?)";
                conn = connect(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, attributeText);
                pstmt.executeUpdate();
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new attribute has been entered.");
                    System.out.println("Database: " + dbName + "\nTable: " + tableName + "\nAttribute: " + attributeName
                            + "\nAttribute Value: " + attributeText + "\n");
                }
            } catch (SQLException e) {
                System.out.println("Error en base de datos");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

    public void insertNumber(String dbName, String tableName, String attributeName, Integer attributeValue) {

        for (String i : url) {
            try {
                sql = "INSERT INTO " + tableName + "(" + attributeName + ") VALUES (?)";
                conn = connect(i);
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, attributeValue);
                pstmt.executeUpdate();
                if (conn != null) {
                    DatabaseMetaData meta = conn.getMetaData();
                    System.out.println("The driver name is " + meta.getDriverName());
                    System.out.println("A new attribute has been entered.");
                    System.out.println("Database: " + dbName + "\nTable: " + tableName + "\nAttribute: " + attributeName
                            + "\nAttribute Value: " + attributeValue + "\n");
                }
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.exit(1);
            } finally {
                try {
                    if (pstmt != null)
                        pstmt.close();
                    if (conn != null)
                        conn.close();
                } catch (SQLException e) {
                    System.out.println("Error.");
                    System.out.println("Detalle del error: \n" + e.getMessage());
                    System.exit(1);
                }
            }
        }
    }

}
