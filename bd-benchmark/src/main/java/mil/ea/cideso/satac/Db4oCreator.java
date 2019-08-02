package mil.ea.cideso.satac;

import mil.ea.cideso.satac.AmenazaWrapper;

import java.util.Iterator;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;
import com.db4o.query.*;

public class Db4oCreator extends MotorBD {

    ObjectContainer db = null;
    private int cantidadAInsertar;

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
        setDbName(dbName);
        try {
            getTimer().start();
            db = getDb(getDbName());
            getTimer().stop();
            System.out.println("La BD se ha generado correctamente.\n");
            setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset()); // Reseteo el timer.
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        } finally {
            db.close();
        }
    }

    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        try {
            getTimer().start();
            db = getDb(getDbName());

            for (int i = 0; i < cantidadAInsertar; i++) {
                Tiempo tiempo = new Tiempo(i, 1);
                Posicion posicion = new Posicion(i, 1.5, 1.5, 1);
                Equipamiento equipamiento = new Equipamiento(i, 1, 1, 1);
                Informante informante = new Informante(i);
                Amenaza amenaza = new Amenaza(i, tiempo, 1, posicion, 1, 1, 1, equipamiento, informante);
                AmenazaWrapper amenazaWrapper = new AmenazaWrapper(i, amenaza, true, false);
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
            setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset()); // Reseteo el timer.
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void readData() {
        try {
            getTimer().start();
            db = getDb(getDbName());

            Query query = db.query();
            query.constrain(AmenazaWrapper.class);
            ObjectSet<AmenazaWrapper> result = query.execute();

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
    public void updateData() {
        try {
            getTimer().start();
            db = getDb(getDbName());

            Query query = db.query();
            query.constrain(AmenazaWrapper.class);
            ObjectSet<AmenazaWrapper> result = query.execute();
            Iterator<AmenazaWrapper> itr = result.iterator();

            while (itr.hasNext()) {
                AmenazaWrapper amenazaWrapper = itr.next();
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
                db.store(amenazaWrapper);
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
    public void deleteData() {
        try {
            getTimer().start();
            db = getDb(getDbName());

            Query query = db.query();
            query.constrain(AmenazaWrapper.class);
            ObjectSet<AmenazaWrapper> result = query.execute();
            Iterator<AmenazaWrapper> itr = result.iterator();

            while (itr.hasNext()) {
                db.delete(itr.next());
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
    public void dropDatabase() {

    }

    public ObjectContainer getDb() {
        return db;
    }

    public void setDb(ObjectContainer db) {
        this.db = db;
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }
}