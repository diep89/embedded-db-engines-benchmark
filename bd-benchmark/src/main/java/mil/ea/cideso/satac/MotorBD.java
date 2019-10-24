package mil.ea.cideso.satac;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.google.common.base.Stopwatch;

public abstract class MotorBD {
    // Atributos comunes
    private String engineName;
    private String engineVersion;
    private String providerName;
    private String providerVersion;
    private String dbName;

    // Atributos JDBC
    private Connection conn = null;

    // Atributos JPA
    private EntityManagerFactory emf = null;

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

    public void initializeJdbcConnection(String jdbcDriver, String user, String pass, String dbUrl) {
        try {
            Class.forName(jdbcDriver);
            if (getConn() == null) {
                setConn(DriverManager.getConnection(dbUrl, user, pass));
            }
        } catch (ClassNotFoundException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        }
    }

    public abstract void insertData(int cantidadAInsertar);

    public List<AmenazaWrapper> generateTestObjects(int cant) {
        List<AmenazaWrapper> testObjectsList = new ArrayList<>(cant);
        for (int i = 0; i < cant; i++) {
            testObjectsList.add(generateAmenazaWrapper());
        }

        return testObjectsList;
    }

    public void persistTestObjects(List<AmenazaWrapper> list) {
        Iterator<AmenazaWrapper> listItr = list.iterator();
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            while (listItr.hasNext()) {
                AmenazaWrapper testObject = listItr.next();
                em.persist(testObject);
            }

            txn.commit();
        } catch (PersistenceException e) {
            if (txn.isActive()) {
                txn.rollback();
            } else {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.out.println("\nStacktrace:\n\n");
                e.printStackTrace();
            }
        } finally {
            em.close();
        }
    }

    // Método para generar Amenazas (para operación INSERT)
    public AmenazaWrapper generateAmenazaWrapper() {
        Tiempo tiempo = new Tiempo();
        tiempo.setEpoch(1);

        Posicion posicion = new Posicion();
        posicion.setLatitud(1.5);
        posicion.setLongitud(1.5);
        posicion.setMilisegundosFechaHora(1);

        Equipamiento equipamiento1 = new Equipamiento();
        equipamiento1.setCantidad(1);
        equipamiento1.setEquipo(1);
        equipamiento1.setTipo(1);

        Equipamiento equipamiento2 = new Equipamiento();
        equipamiento2.setCantidad(1);
        equipamiento2.setEquipo(1);
        equipamiento2.setTipo(1);

        Equipamiento equipamiento3 = new Equipamiento();
        equipamiento3.setCantidad(1);
        equipamiento3.setEquipo(1);
        equipamiento3.setTipo(1);

        Informante informante = new Informante();

        Amenaza amenaza = new Amenaza();
        amenaza.setTiempo(tiempo);
        amenaza.setPosicion(posicion);
        amenaza.addEquipamientoItem(equipamiento1);
        amenaza.addEquipamientoItem(equipamiento2);
        amenaza.addEquipamientoItem(equipamiento3);
        amenaza.setInformante(informante);
        amenaza.setCodigoSimbolo(1);
        amenaza.setRadioAccion(1);
        amenaza.setIdentificacion(1);
        amenaza.setTamanios(1);

        AmenazaWrapper amenazaWrapper = new AmenazaWrapper();
        amenazaWrapper.setAmenaza(amenaza);
        amenazaWrapper.setVisible(true);
        amenazaWrapper.setLeido(false);

        return amenazaWrapper;
    }

    public abstract void readData();

    public void readTestObjects() {
        EntityManager em = getEmf().createEntityManager();

        try {
            Query query = em.createQuery("SELECT p FROM AmenazaWrapper p", AmenazaWrapper.class);
            List results = query.getResultList();
            Query query2 = em.createQuery("SELECT p FROM Amenaza p", Amenaza.class);
            List results2 = query2.getResultList();
            Query query3 = em.createQuery("SELECT p FROM Equipamiento p", Equipamiento.class);
            List results3 = query3.getResultList();
            Query query4 = em.createQuery("SELECT p FROM Informante p", Informante.class);
            List results4 = query4.getResultList();
            Query query5 = em.createQuery("SELECT p FROM Posicion p", Posicion.class);
            List results5 = query5.getResultList();
            Query query6 = em.createQuery("SELECT p FROM Tiempo p", Tiempo.class);
            List results6 = query6.getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public abstract void updateData();

    public void updateTestObjects() {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            TypedQuery<AmenazaWrapper> query = em.createQuery("SELECT p FROM AmenazaWrapper p", AmenazaWrapper.class);
            List<AmenazaWrapper> results = query.getResultList();
            Iterator<AmenazaWrapper> resultsItr = results.iterator();

            while (resultsItr.hasNext()) {
                AmenazaWrapper amenazaWrapper = resultsItr.next();

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

            txn.commit();

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public abstract void deleteData();

    public void deleteTestObjects() {
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            TypedQuery<AmenazaWrapper> query = em.createQuery("SELECT p FROM AmenazaWrapper p", AmenazaWrapper.class);
            List<AmenazaWrapper> results = query.getResultList();
            Iterator<AmenazaWrapper> resultsItr = results.iterator();
            while (resultsItr.hasNext()) {
                AmenazaWrapper testObject = resultsItr.next();
                em.remove(testObject);
            }

            txn.commit();

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } finally {
            em.close();
            if (getEmf() != null)
                getEmf().close();
        }
    }

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

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}