package mil.ea.cideso.satac;

import mil.ea.cideso.satac.Db4oAmenazaWrapper;

import java.util.Iterator;

// import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;
import com.db4o.query.*;

public class Db4oCreator extends MotorBD {

    ObjectContainer db = null;
    // private int cantidadAInsertar;

    public Db4oCreator() {
        setEngineName("db4o");
        setEngineVersion("7.7.67");
    }

    public ObjectContainer getDb(String dbName) {
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbName);
        return db;
    }

    @Override
    public void createNewDatabase(String dbName) {
        try {
            db = getDb(dbName);
            System.out.println("La BD se ha generado correctamente.\n");
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        } finally {
            db.close();
        }
    }

    @Override
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {
        // this.cantidadAInsertar = cantidadAInsertar;

        try {
            getTimer().start();
            db = getDb(dbName);

            for (int i = 0; i < cantidadAInsertar; i++) {
                Db4oTiempo tiempo = new Db4oTiempo(1);
                Db4oPosicion posicion = new Db4oPosicion(1.5, 1.5, 1);
                Db4oEquipamiento equipamiento = new Db4oEquipamiento(1, 1, 1);
                Db4oInformante informante = new Db4oInformante("Test");
                Db4oAmenaza amenaza = new Db4oAmenaza(i, tiempo, 1, posicion, 1, 1, 1, equipamiento, informante);
                Db4oAmenazaWrapper amenazaWrapper = new Db4oAmenazaWrapper(amenaza, true, false);
                db.store(amenazaWrapper);
                getTimer().stop();
                if (i + 1 < cantidadAInsertar) {
                    System.out.print((i + 1) + " - ");
                } else {
                    System.out.print((i + 1) + ".");
                }
                getTimer().start();
            }
            db.close();
            getTimer().stop();

            System.out.println("");
            System.out.println("");
            setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset()); // Reseteo el timer.
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void readData(String dbName, String tableName) {
        try {
            getTimer().start();
            db = getDb(dbName);

            Query query = db.query();
            query.constrain(Db4oAmenazaWrapper.class);
            ObjectSet<Db4oAmenazaWrapper> result = query.execute();

            getTimer().stop();

            if (result != null) {
                System.out.println("Registros leídos correctamente.\n");
            }

            getTimer().start();
            db.close();
            getTimer().stop();

            System.out.println("");
            setStatsReadOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        try {
            getTimer().start();
            db = getDb(dbName);

            Query query = db.query();
            query.constrain(Db4oAmenazaWrapper.class);
            ObjectSet<Db4oAmenazaWrapper> result = query.execute();
            Iterator<Db4oAmenazaWrapper> db4oItr = result.iterator();

            int i = 0;

            while (db4oItr.hasNext()) {
                Db4oAmenazaWrapper amenazaWrapper = db4oItr.next();

                Db4oTiempo tiempo = new Db4oTiempo(2);
                Db4oPosicion posicion = new Db4oPosicion(2.5, 2.5, 2);
                Db4oEquipamiento equipamiento = new Db4oEquipamiento(2, 2, 2);
                Db4oInformante informante = new Db4oInformante("Test2");
                Db4oAmenaza amenaza = new Db4oAmenaza(i, tiempo, 2, posicion, 2, 2, 2, equipamiento, informante);

                amenazaWrapper.setAmenaza(amenaza);
                amenazaWrapper.setLeido(true);
                db.store(amenazaWrapper);
                i++;
            }

            db.close();
            getTimer().stop();
            System.out.println("Registros actualizados correctamente.\n");

            System.out.println("");
            setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }

    }

    @Override
    public void deleteData(String dbName, String tableName) {
        try {
            getTimer().start();
            db = getDb(dbName);

            Query query = db.query();
            query.constrain(Db4oAmenazaWrapper.class);
            ObjectSet<Db4oAmenazaWrapper> result = query.execute();
            Iterator<Db4oAmenazaWrapper> db4oItr = result.iterator();

            while (db4oItr.hasNext()) {
                db.delete(db4oItr.next());
            }

            getTimer().stop();
            System.out.println("Registros eliminados correctamente.\n");
            System.out.println("");

            getTimer().start();
            db.close();
            getTimer().stop();

            setStatsDeleteOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }

    }

    @Override
    public void dropDatabase(String dbName) {

    }
}