// Comando SQL para ver la estructura de una BD SQLite:
// SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';
// Comando SQL Para ver la estructura de una tabla en la BD SQLite:
// SELECT sql FROM sqlite_master WHERE name = 'AmenazaWrapper';
// SELECT * FROM AmenazaWrapper;
// SELECT * FROM Amenaza;

package mil.ea.cideso.satac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.sql.DatabaseMetaData;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class SqliteCreator extends MotorBD {

    private int cantidadAInsertar;
    private String url;
    private static Connection conn = null;
    private EntityManagerFactory emf;
    // Este valor debe coincidir con el especificado en la propiedad
    // 'hibernate.jdbc.batch_size', en la persistence-unit 'SQLitePersistence' del
    // archivo persistence.xml
    private int batchSize = 20;

    public SqliteCreator() {
        setEngineName("SQLite");
        setEngineVersion("3.28.0");
        setProviderName("Hibernate JPA");
        setProviderVersion("5.4.3");
    }

    // Función para operación CREATE
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);
        // String url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
        setUrl("jdbc:sqlite:" + getDbName() + ".db");

        try {
            getTimer().start();
            getConnection(getUrl());
            getTimer().stop();
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha generado correctamente.\n");
                setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
                setTimer(getTimer().reset()); // Reseteo el timer.
            }

        } catch (SQLException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
        }

        finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                System.out.println("Error.");
                System.out.println("Detalle del error: \n" + e.getMessage());
                System.out.println("\nStacktrace:\n\n");
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    // Función para operación INSERT
    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);
        int j = 0;
        getTimer().start();
        setEmf(Persistence.createEntityManagerFactory("SQLitePersistence"));
        EntityManager em = getEmf().createEntityManager();
        EntityTransaction txn = em.getTransaction();

        try {
            txn.begin();

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                // generarAmenazaWrapperInsert() genera todos los objetos que componen una
                // amenaza, con todos sus atributos inicializados y todas sus relaciones
                // declaradas, y los devuelve en una lista.
                List<Object> newAmenazaList = generarAmenazaWrapper(i);
                Iterator<Object> newAmenazaListItr = newAmenazaList.iterator();

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
                System.exit(1);
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

            // Comprobación
            // Iterator<AmenazaWrapper> itr = results.iterator();
            // System.out.println("");
            // while (itr.hasNext()) {
            // AmenazaWrapper el = itr.next();
            // System.out.printf("AmenazaWrapper\nId: %d\nLeido: %b\nVisible: %b\n\n",
            // el.getId(), el.isLeido(),
            // el.isVisible());
            // }
            // System.out.println("");

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
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
            System.exit(1);
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
            System.exit(1);
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
    public Connection getConnection(String url) {
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(url);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public Connection getConn() {
        return conn;
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
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

}
