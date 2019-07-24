// Comando SQL para ver la estructura de una BD SQLite:
// SELECT name FROM sqlite_master WHERE type ='table' AND name NOT LIKE 'sqlite_%';
// Comando SQL Para ver la estructura de una tabla en la BD SQLite:
// SELECT sql FROM sqlite_master WHERE name = 'AmenazaWrapper';
// SELECT * FROM AmenazaWrapper;
// SELECT * FROM Amenaza;

package mil.ea.cideso.satac;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
// import java.util.Iterator;
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
    private String sql;

    // Atributos para conexión SQL
    private static Connection conn = null;
    private Statement stmt;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private EntityManagerFactory emf;

    public SqliteCreator() {
        setEngineName("SQLite");
        setEngineVersion("3.27.2.1");
        setProviderName("Hibernate");
        setProviderVersion("5.4.3");
    }

    // Función para crear una nueva BD.
    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);
        // String url = "jdbc:sqlite:C:/sqlite/db/" + dbName;
        setUrl("jdbc:sqlite:" + getDbName() + ".db");

        try {
            getConnection(getUrl());
            if (getConn() != null) {
                DatabaseMetaData meta = getConn().getMetaData();
                System.out.println("Driver: " + meta.getDriverName());
                System.out.println("La BD se ha generado correctamente.\n");
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

    // Función para operación CREATE
    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        try {
            getTimer().start();
            // getConnection(getUrl());
            setEmf(Persistence.createEntityManagerFactory("SQLitePersistence"));

            for (int i = 0; i < getCantidadAInsertar(); i++) {
                Tiempo tiempo = new Tiempo(i, 1);
                Posicion posicion = new Posicion(i, 1.5, 1.5, 1);
                Equipamiento equipamiento = new Equipamiento(i, 1, 1, 1);
                Informante informante = new Informante(i);
                Amenaza amenaza = new Amenaza(i, tiempo, 1, posicion, 1, 1, 1, equipamiento, informante);
                AmenazaWrapper amenazaWrapper = new AmenazaWrapper(i, amenaza, true, false);

                // Declaro relaciones
                amenazaWrapper.setAmenaza(amenaza);
                amenaza.setAmenazaWrapper(amenazaWrapper);
                amenaza.setInformante(informante);
                amenaza.setEquipamiento(equipamiento);
                amenaza.setPosicion(posicion);
                amenaza.setTiempo(tiempo);
                informante.setAmenaza(amenaza);
                equipamiento.setAmenaza(amenaza);
                posicion.setAmenaza(amenaza);
                tiempo.setAmenaza(amenaza);

                // Persisto objetos
                EntityManager em = getEmf().createEntityManager();
                EntityTransaction txn = em.getTransaction();
                txn.begin();
                em.persist(amenazaWrapper);
                em.persist(amenaza);
                em.persist(informante);
                em.persist(equipamiento);
                em.persist(posicion);
                em.persist(tiempo);
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

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    // ********************************************
    // EJEMPLO PARA CONSULTA

    // // Find the number of Point objects in the database:
    // Query q1 = em.createQuery("SELECT COUNT(p) FROM Point p");
    // System.out.println("Total Points: " + q1.getSingleResult());

    // // Find the average X value:
    // Query q2 = em.createQuery("SELECT AVG(p.x) FROM Point p");
    // System.out.println("Average X: " + q2.getSingleResult());

    // // Retrieve all the Point objects from the database:
    // TypedQuery<Point> query = em.createQuery("SELECT p FROM Point p",
    // Point.class);
    // List<Point> results = query.getResultList();
    // for (Point p : results) {
    // System.out.println(p);
    // }

    // ********************************************

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
            // System.out.printf("AmenazaWrapper\nId: %d\n Leido: %b\n Visible: %b\n\n",
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
                amenazaWrapper.getAmenaza().getTiempo().setEpoch(2);
                amenazaWrapper.getAmenaza().setCodigoSimbolo(2);
                amenazaWrapper.getAmenaza().getPosicion().setLatitud(2.5);
                amenazaWrapper.getAmenaza().getPosicion().setLongitud(2.5);
                amenazaWrapper.getAmenaza().getPosicion().setMilisegundosFechaHora(2);
                amenazaWrapper.getAmenaza().setRadioAccion(2);
                amenazaWrapper.getAmenaza().setIdentificacion(2);
                amenazaWrapper.getAmenaza().setTamanios(2);
                amenazaWrapper.getAmenaza().getEquipamiento().setCantidad(2);
                amenazaWrapper.getAmenaza().getEquipamiento().setEquipo(2);
                amenazaWrapper.getAmenaza().getEquipamiento().setTipo(2);
                amenazaWrapper.setLeido(true);
                txn.commit();
                em.close();
            }

            getTimer().stop();

            // Comprobación
            // EntityManager em = getEmf().createEntityManager();
            // TypedQuery<AmenazaWrapper> query = em.createQuery("SELECT p FROM
            // AmenazaWrapper p", AmenazaWrapper.class);
            // List<AmenazaWrapper> results = query.getResultList();
            // Iterator<AmenazaWrapper> itr = results.iterator();

            // while (itr.hasNext()) {
            // AmenazaWrapper el = itr.next();
            // System.out.printf("AmenazaWrapper\n Id: %d\n Leido: %b\n Visible: %b\n",
            // el.getId(), el.isLeido(),
            // el.isVisible());
            // }
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
                Amenaza amenaza = em.find(Amenaza.class, i);
                Tiempo tiempo = em.find(Tiempo.class, i);
                Equipamiento equipamiento = em.find(Equipamiento.class, i);
                Informante informante = em.find(Informante.class, i);
                Posicion posicion = em.find(Posicion.class, i);
                EntityTransaction txn = em.getTransaction();
                txn.begin();
                em.remove(amenazaWrapper);
                em.remove(amenaza);
                em.remove(tiempo);
                em.remove(equipamiento);
                em.remove(informante);
                em.remove(posicion);
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

    /**
     * @return the sql
     */
    public String getSql() {
        if (sql == null) {
            sql = new String();
        }
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public EntityManagerFactory getEmf() {
        return emf;
    }

    public void setEmf(EntityManagerFactory emf) {
        this.emf = emf;
    }

}
