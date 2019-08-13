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

    private int cantidadAInsertar;

    private String url;

    // Atributos para conexión SQL
    private static Connection conn;

    private EntityManagerFactory emf;

    public H2Creator() {
        setEngineName("H2");
        setEngineVersion("1.4.199");
        setProviderName("Hibernate");
        setProviderVersion("5.4.3");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);
        setUrl("jdbc:h2:./" + getDbName());

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
            System.out.println(e.getMessage());
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

    // Función para operación CREATE
    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        try {
            getTimer().start();
            setEmf(Persistence.createEntityManagerFactory("H2Persistence"));

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                // generarAmenazaWrapperInsert() genera todos los objetos que componen una
                // amenaza, con todos sus atributos inicializados y todas sus relaciones
                // declaradas, y los devuelve en una lista.
                List<Object> newAmenazaList = generarAmenazaWrapper(i);
                Iterator<Object> newAmenazaListItr = newAmenazaList.iterator();

                EntityManager em = getEmf().createEntityManager();
                EntityTransaction txn = em.getTransaction();
                txn.begin();

                while (newAmenazaListItr.hasNext()) {
                    Object element = newAmenazaListItr.next();
                    em.persist(element);
                }

                txn.commit();
                em.close();

                getTimer().stop();

                if (i + 1 < getCantidadAInsertar()) {
                    System.out.print((i + 1) + " - ");
                } else {
                    System.out.print((i + 1) + ".");
                }

                getTimer().start();
            }
        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
        }

        getTimer().stop();

        System.out.println("");
        System.out.println("");

        setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // Función para operación READ
    @Override
    public void readData() {

        try {
            getTimer().start();

            EntityManager em = getEmf().createEntityManager();
            TypedQuery<AmenazaWrapper> query = em.createQuery("SELECT p FROM AmenazaWrapper p", AmenazaWrapper.class);
            List<AmenazaWrapper> results = query.getResultList();

            getTimer().stop();

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

            System.out.println("Registros leídos correctamente.");

            System.out.println("");
            System.out.println("");

            setStatsReadOperation(getTimer().toString()); // Guardo el valor del timer
            setTimer(getTimer().reset()); // Reseteo el timer.

        } catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
        }

    }

    // Función para operación UPDATE
    @Override
    public void updateData() {
        try {
            getTimer().start();

            for (int i = 0; i < getCantidadAInsertar(); i++) {
                EntityManager em = getEmf().createEntityManager();
                AmenazaWrapper amenazaWrapper = em.find(AmenazaWrapper.class, i);

                EntityTransaction txn = em.getTransaction();
                txn.begin();

                // generarAmenazaWrapperUpdate() recibe el objeto amenazaWrapper (obtenido de la
                // BD) y modifica el valor de todos sus atributos, como también, el valor de
                // todos los atributos de todos los objetos que componen la Amenaza (Amenaza,
                // Tiempo, Posicion, etc).
                updateAmenazaWrapper(amenazaWrapper);

                txn.commit();
                em.close();
            }

            getTimer().stop();

        }

        catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("Registros actualizados correctamente.");
        System.out.println("");
        System.out.println("");
        setStatsUpdateOperation(getTimer().toString());
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void deleteData() {
        try {
            getTimer().start();

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                EntityManager em = getEmf().createEntityManager();
                AmenazaWrapper amenazaWrapper = em.find(AmenazaWrapper.class, i);
                List<Object> amenazaWrapperList = obtenerAmenazaWrapper(amenazaWrapper);
                Iterator<Object> amenazaWrapperListItr = amenazaWrapperList.iterator();

                EntityTransaction txn = em.getTransaction();
                txn.begin();

                while (amenazaWrapperListItr.hasNext()) {
                    Object element = amenazaWrapperListItr.next();
                    em.remove(element);
                }

                em.remove(amenazaWrapper);

                txn.commit();
                em.close();

            }

            getTimer().stop();

            System.out.println("Registros eliminados correctamente.");
        }

        catch (PersistenceException e) {
            System.out.println("Error.");
            System.out.println("Detalle del error: \n" + e.getMessage());
            System.out.println("\nStacktrace:\n\n");
            e.printStackTrace();
            System.exit(1);
        }

        finally {
            if (getEmf() != null)
                getEmf().close();
        }

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

    public Connection getConn() {
        return conn;
    }

    public Connection getConnection(String url) {
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    // public ResultSet getRs() {
    // return rs;
    // }

    // public void setRs(ResultSet rs) {
    // this.rs = rs;
    // }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
