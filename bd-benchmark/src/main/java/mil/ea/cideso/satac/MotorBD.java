package mil.ea.cideso.satac;

// import java.util.ArrayList;
import java.sql.Connection;
// import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.google.common.base.Stopwatch;

public abstract class MotorBD {
    private String nombreMotor;
    private String url;
    private String sql;

    // Lista de atributos para generar la tabla de prueba
    private String[] attributesList;
    private String[] attributesType;
    private String[] attributesLength;
    private int attributesQty;

    // Atributos para conexión SQL
    private Connection conn;
    private Statement stmt;
    private PreparedStatement pstmt;

    // Timer
    private Stopwatch timer;
    // Stopwatch timer = Stopwatch.createUnstarted();

    // Atributos para guardar resultados de pruebas
    String statsCreateOp;

    /**
     * @return the nombreMotor
     */
    public String getNombreMotor() {
        if (nombreMotor == null) {
            nombreMotor = new String();
        }
        return nombreMotor;
    }

    /**
     * @param nombreMotor the nombreMotor to set
     */
    public void setNombreMotor(String nombreMotor) {
        this.nombreMotor = nombreMotor;
    }

    /**
     * @return the pstmt
     */
    public PreparedStatement getPstmt() {
        return pstmt;
    }

    /**
     * @return the stmt
     */
    public Statement getStmt() {
        return stmt;
    }

    /**
     * @param stmt the stmt to set
     */
    public void setStmt(Statement stmt) {
        this.stmt = stmt;
    }

    /**
     * @param pstmt the pstmt to set
     */
    public void setPstmt(PreparedStatement pstmt) {
        this.pstmt = pstmt;
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

    /**
     * @param url the url to set
     */
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

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the attributesList
     */
    public String[] getAttributesList() {
        return attributesList;
    }

    /**
     * @param attributesList the attributesList to set
     */
    public void setAttributesList(String[] attributesList) {
        this.attributesList = attributesList;
    }

    /**
     * @return the attributesType
     */
    public String[] getAttributesType() {
        return attributesType;
    }

    /**
     * @param attributesType the attributesType to set
     */
    public void setAttributesType(String[] attributesType) {
        this.attributesType = attributesType;
    }

    /**
     * @return the attributesLength
     */
    public String[] getAttributesLength() {
        return attributesLength;
    }

    /**
     * @param attributesLength the attributesLength to set
     */
    public void setAttributesLength(String[] attributesLength) {
        this.attributesLength = attributesLength;
    }

    /**
     * @return the attributesQty
     */
    public int getAttributesQty() {
        return attributesQty;
    }

    /**
     * @param attributesQty the attributesQty to set
     */
    public void setAttributesQty(int attributesQty) {
        this.attributesQty = attributesQty;
    }

    /**
     * @return the conn
     */
    public Connection getConn() {
        return conn;
    }

    /**
     * @param conn the conn to set
     */
    public void setConn(Connection conn) {
        this.conn = conn;
    }

    /**
     * @return the timer
     */
    public Stopwatch getTimer() {
        if (timer == null) {
            timer = Stopwatch.createUnstarted();
        }
        return timer;
    }

    /**
     * @param timer the timer to set
     */
    public void setTimer(Stopwatch timer) {
        this.timer = timer;
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

    /**
     * @return the statsCreateOp
     */
    public String getStatsCreateOp() {
        return statsCreateOp;
    }

    /**
     * @param statsCreateOp the statsCreateOp to set
     */
    public void setStatsCreateOp(String statsCreateOp) {
        this.statsCreateOp = statsCreateOp;
    }

    public abstract void createNewDatabase(String dbName);

    public abstract void createNewTable(String dbName, String tableName, String[] attributesList,
            String[] attributesType, String[] attributesLength);

    public abstract void insertData(String dbName, String tableName, int cantidadAInsertar);

    public abstract void dropDatabase(String dbName);

    // Funciones para configurar test automatizado

    // public abstract void createNewAttribute(String dbName, String tableName,
    // String AttributeName, String attributeType,
    // Integer attributeSize);

    // public abstract void insertText(String dbName, String tableName, String
    // attributeName, String attributeText);

    // public abstract void insertNumber(String dbName, String tableName, String
    // attributeName, Integer attributeValue);

}