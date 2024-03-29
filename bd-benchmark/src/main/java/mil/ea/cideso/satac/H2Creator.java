package mil.ea.cideso.satac;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class H2Creator extends MotorBD {
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "org.h2.Driver";
    private String DB_URL;

    // Database credentials
    static final String USER = "sa";
    static final String PASS = "";

    private int cantidadAInsertar;
    // Atributos para conexión SQL
    private static Connection conn;
    private EntityManagerFactory emf;
    // Este valor debe coincidir con el especificado en la propiedad
    // 'hibernate.jdbc.batch_size', en la persistence-unit 'H2Persistence' del
    // archivo persistence.xml
    private int batchSize = 20;

    public H2Creator() {
        setEngineName("H2");
        setEngineVersion("1.4.199");
        setProviderName("Hibernate JPA");
        setProviderVersion("5.4.3");
    }

    // Función para operación CREATE
    @Override
    public void createNewDatabase(String dbName) {
        getTimer().start();

        setDbName(dbName);
        setDB_URL("jdbc:h2:./" + getDbName());

        try {
            Class.forName(JDBC_DRIVER);
            setConnection(getDB_URL(), USER, PASS);
            getTimer().stop();
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha generado correctamente.\n");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.out.println("\nStacktrace:\n\n");
                e.printStackTrace();
            }
        }
        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación INSERT
    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);
        int j = 0;
        getTimer().start();
        setEmf(Persistence.createEntityManagerFactory("H2Persistence"));
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                // generarAmenazaWrapperInsert() genera todos los objetos que componen una
                // amenaza, con todos sus atributos inicializados y todas sus relaciones
                // declaradas, y los devuelve en una lista.
                // El tiempo de generación de los objetos de prueba se excluye de las mediciones
                getTimer().stop();
                List<Object> newAmenazaList = generarAmenazaWrapper(i);
                Iterator<Object> newAmenazaListItr = newAmenazaList.iterator();
                getTimer().start();

                while (newAmenazaListItr.hasNext()) {
                    if (j % getBatchSize() == 0 && j > 0) {
                        txn.commit();
                        txn.begin();
                        em.clear();
                    }
                    Object element = newAmenazaListItr.next();
                    em.persist(element);
                    j++;
                }

                getTimer().stop();
                if (i + 1 < getCantidadAInsertar()) {
                    System.out.print((i + 1) + " - ");
                } else {
                    System.out.print((i + 1) + ".");
                }
                getTimer().start();
            }

            txn.commit();

            getTimer().stop();

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

        System.out.println("");
        System.out.println("");

        setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación READ
    @Override
    public void readData() {
        getTimer().start();

        EntityManager em = getEmf().createEntityManager();

        try {
            TypedQuery<AmenazaWrapper> query = em.createQuery("SELECT p FROM AmenazaWrapper p", AmenazaWrapper.class);
            List<AmenazaWrapper> results = query.getResultList();
            TypedQuery<Amenaza> query2 = em.createQuery("SELECT p FROM Amenaza p", Amenaza.class);
            List<Amenaza> results2 = query2.getResultList();
            TypedQuery<Equipamiento> query3 = em.createQuery("SELECT p FROM Equipamiento p", Equipamiento.class);
            List<Equipamiento> results3 = query3.getResultList();
            TypedQuery<Informante> query4 = em.createQuery("SELECT p FROM Informante p", Informante.class);
            List<Informante> results4 = query4.getResultList();
            TypedQuery<Posicion> query5 = em.createQuery("SELECT p FROM Posicion p", Posicion.class);
            List<Posicion> results5 = query5.getResultList();
            TypedQuery<Tiempo> query6 = em.createQuery("SELECT p FROM Tiempo p", Tiempo.class);
            List<Tiempo> results6 = query6.getResultList();

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } finally {
            em.close();
            getTimer().stop();
        }

        System.out.println("Registros leídos correctamente.");

        System.out.println("");
        System.out.println("");

        setStatsReadOperation(getTimer().toString()); // Guardo el valor del timer
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación UPDATE
    @Override
    public void updateData() {
        getTimer().start();

        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            for (int i = 0; i < getCantidadAInsertar(); i++) {
                AmenazaWrapper amenazaWrapper = em.find(AmenazaWrapper.class, i);

                // generarAmenazaWrapperUpdate() recibe el objeto amenazaWrapper (obtenido de la
                // BD) y modifica el valor de todos sus atributos, como también, el valor de
                // todos los atributos de todos los objetos que componen la Amenaza (Amenaza,
                // Tiempo, Posicion, etc).
                updateAmenazaWrapper(amenazaWrapper);
            }

            txn.commit();

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
        } finally {
            em.close();
            getTimer().stop();
        }

        System.out.println("Registros actualizados correctamente.");
        System.out.println("");
        System.out.println("");

        setStatsUpdateOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación DELETE
    @Override
    public void deleteData() {
        getTimer().start();

        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                AmenazaWrapper amenazaWrapper = em.find(AmenazaWrapper.class, i);
                List<Object> amenazaWrapperList = obtenerAmenazaWrapper(amenazaWrapper);
                Iterator<Object> amenazaWrapperListItr = amenazaWrapperList.iterator();

                while (amenazaWrapperListItr.hasNext()) {
                    Object element = amenazaWrapperListItr.next();
                    em.remove(element);
                }
                em.remove(amenazaWrapper);
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
            getTimer().stop();
        }

        System.out.println("Registros eliminados correctamente.");
        System.out.println("");
        System.out.println("");

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

    // Función para generar la conexión a la BD
    public void setConnection(String dbUrl, String user, String pass) {
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(dbUrl, user, pass);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getDB_URL() {
        return DB_URL;
    }

    public void setDB_URL(String dB_URL) {
        DB_URL = dB_URL;
    }
}
