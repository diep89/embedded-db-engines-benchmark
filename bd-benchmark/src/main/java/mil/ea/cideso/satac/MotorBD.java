package mil.ea.cideso.satac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

    // Método para generar Amenazas (para operación INSERT)
    // Recibe el parámetro id, el cual será establecido como el id
    // del objeto AmenazaWrapper dentro de la BD.
    // Devuelve una lista con todos los objetos que componen la amenaza generada.
    public List<Object> generarAmenazaWrapper(int id) {
        List<Object> newAmenazaList = new ArrayList<>();

        Tiempo tiempo = new Tiempo();
        tiempo.setEpoch(1);
        newAmenazaList.add(tiempo);

        Posicion posicion = new Posicion();
        posicion.setLatitud(1.5);
        posicion.setLongitud(1.5);
        posicion.setMilisegundosFechaHora(1);
        newAmenazaList.add(posicion);

        Equipamiento equipamiento = new Equipamiento();
        equipamiento.setCantidad(1);
        equipamiento.setEquipo(1);
        equipamiento.setTipo(1);
        newAmenazaList.add(equipamiento);

        Equipamiento equipamiento2 = new Equipamiento();
        equipamiento2.setCantidad(1);
        equipamiento2.setEquipo(1);
        equipamiento2.setTipo(1);
        newAmenazaList.add(equipamiento2);

        Equipamiento equipamiento3 = new Equipamiento();
        equipamiento3.setCantidad(1);
        equipamiento3.setEquipo(1);
        equipamiento3.setTipo(1);
        newAmenazaList.add(equipamiento3);

        List<Equipamiento> equipamientoList = new ArrayList<>();
        equipamientoList.add(equipamiento);
        equipamientoList.add(equipamiento2);
        equipamientoList.add(equipamiento3);

        Informante informante = new Informante();
        newAmenazaList.add(informante);

        Amenaza amenaza = new Amenaza();
        amenaza.setTiempo(tiempo);
        amenaza.setCodigoSimbolo(1);
        amenaza.setPosicion(posicion);
        amenaza.setRadioAccion(1);
        amenaza.setIdentificacion(1);
        amenaza.setTamanios(1);
        amenaza.setEquipamientoList(equipamientoList);
        amenaza.setInformante(informante);
        newAmenazaList.add(amenaza);

        AmenazaWrapper amenazaWrapper = new AmenazaWrapper(id, amenaza, true, false);
        newAmenazaList.add(amenazaWrapper);

        return newAmenazaList;
    }

    // Método para generar Amenazas (para operación UPDATE)
    // Recibe el objeto AmenazaWrapper para poder acceder a todos los
    // objetos ligados a este (Amenaza, Tiempo, EquipamientoList, etc).
    public void updateAmenazaWrapper(AmenazaWrapper amenazaWrapper) {
        amenazaWrapper.getAmenaza().setCodigoSimbolo(2);
        amenazaWrapper.getAmenaza().setRadioAccion(2);
        amenazaWrapper.getAmenaza().setIdentificacion(2);
        amenazaWrapper.getAmenaza().setTamanios(2);

        amenazaWrapper.getAmenaza().getTiempo().setEpoch(2);

        amenazaWrapper.getAmenaza().getPosicion().setLatitud(2.5);
        amenazaWrapper.getAmenaza().getPosicion().setLongitud(2.5);
        amenazaWrapper.getAmenaza().getPosicion().setMilisegundosFechaHora(2);

        List<Equipamiento> equipamientoList = amenazaWrapper.getAmenaza().getEquipamientoList();
        Iterator<Equipamiento> itrEquipamiento = equipamientoList.iterator();

        while (itrEquipamiento.hasNext()) {
            Equipamiento equip = itrEquipamiento.next();
            equip.setCantidad(2);
            equip.setEquipo(2);
            equip.setTipo(2);
        }

        amenazaWrapper.setLeido(true);
    }

    // Método para devolver todos los elementos que componen una amenaza.
    // Recibe el objeto AmenazaWrapper para poder acceder a todos los
    // objetos ligados a este (Amenaza, Tiempo, EquipamientoList, etc).
    // Devuelve una lista con todos los objetos que componen la amenaza.
    public List<Object> obtenerAmenazaWrapper(AmenazaWrapper amenazaWrapper) {

        List<Object> amenazaWrapperList = new ArrayList<>();

        Amenaza amenaza = amenazaWrapper.getAmenaza();
        amenazaWrapperList.add(amenaza);

        Tiempo tiempo = amenaza.getTiempo();
        amenazaWrapperList.add(tiempo);

        Informante informante = amenaza.getInformante();
        amenazaWrapperList.add(informante);

        Posicion posicion = amenaza.getPosicion();
        amenazaWrapperList.add(posicion);

        List<Equipamiento> equipamientoList = amenaza.getEquipamientoList();
        Iterator<Equipamiento> equipamientoListItr = equipamientoList.iterator();

        while (equipamientoListItr.hasNext()) {
            Equipamiento equip = equipamientoListItr.next();
            amenazaWrapperList.add(equip);
        }

        return amenazaWrapperList;
    }

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