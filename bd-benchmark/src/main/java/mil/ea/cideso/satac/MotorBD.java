package mil.ea.cideso.satac;

import java.io.IOException;

import com.google.common.base.Stopwatch;

public abstract class MotorBD {
    private String engineName;
    private String engineVersion;
    private String providerName;
    private String providerVersion;
    private String dbName;

    // Timer
    private Stopwatch timer = null;
    // Stopwatch timer = Stopwatch.createUnstarted();

    // Atributos para guardar resultados de pruebas
    private String statsCreateOperation;
    private String statsInsertOperation;
    private String statsReadOperation;
    private String statsUpdateOperation;
    private String statsDeleteOperation;

    // Métodos
    public abstract void createNewDatabase(String dbName) throws IOException;

    public abstract void insertData(int cantidadAInsertar);

    public abstract void readData();

    public abstract void updateData();

    public abstract void deleteData();

    public abstract void dropDatabase();

    // Getters y Setters
    /**
     * @return the engineName
     */
    public String getEngineName() {
        if (engineName == null) {
            engineName = new String();
        }
        return engineName;
    }

    /**
     * @return the engineVersion
     */
    public String getEngineVersion() {
        return engineVersion;
    }

    /**
     * @param engineVersion the engineVersion to set
     */
    public void setEngineVersion(String engineVersion) {
        this.engineVersion = engineVersion;
    }

    /**
     * @param nombreMotor the nombreMotor to set
     */
    public void setEngineName(String EngineName) {
        this.engineName = EngineName;
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

    /**
     * @return the statsCreateOperation
     */
    public String getStatsCreateOperation() {
        return statsCreateOperation;
    }

    /**
     * @param statsCreateOperation the statsCreateOperation to set
     */
    public void setStatsCreateOperation(String statsCreateOperation) {
        this.statsCreateOperation = statsCreateOperation;
    }

    /**
     * @return the statsReadOperation
     */
    public String getStatsReadOperation() {
        return statsReadOperation;
    }

    /**
     * @param statsReadOperation the statsReadOperation to set
     */
    public void setStatsReadOperation(String statsReadOperation) {
        this.statsReadOperation = statsReadOperation;
    }

    /**
     * @return the statsUpdateOperation
     */
    public String getStatsUpdateOperation() {
        return statsUpdateOperation;
    }

    /**
     * @param statsUpdateOperation the statsUpdateOperation to set
     */
    public void setStatsUpdateOperation(String statsUpdateOperation) {
        this.statsUpdateOperation = statsUpdateOperation;
    }

    /**
     * @return the statsDeleteOperation
     */
    public String getStatsDeleteOperation() {
        return statsDeleteOperation;
    }

    /**
     * @param statsDeleteOperation the statsDeleteOperation to set
     */
    public void setStatsDeleteOperation(String statsDeleteOperation) {
        this.statsDeleteOperation = statsDeleteOperation;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderVersion() {
        return providerVersion;
    }

    public void setProviderVersion(String providerVersion) {
        this.providerVersion = providerVersion;
    }

    public String getStatsInsertOperation() {
        return statsInsertOperation;
    }

    public void setStatsInsertOperation(String statsInsertOperation) {
        this.statsInsertOperation = statsInsertOperation;
    }

    // Funciones para configurar test automatizado

    // public abstract void createNewAttribute(String dbName, String tableName,
    // String AttributeName, String attributeType,
    // Integer attributeSize);

    // public abstract void insertText(String dbName, String tableName, String
    // attributeName, String attributeText);

    // public abstract void insertNumber(String dbName, String tableName, String
    // attributeName, Integer attributeValue);

}