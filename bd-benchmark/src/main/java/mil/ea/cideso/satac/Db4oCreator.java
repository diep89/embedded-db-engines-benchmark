package mil.ea.cideso.satac;

import java.util.Iterator;
import java.util.List;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;
import com.db4o.query.Query;

public class Db4oCreator extends MotorBD {

    private ObjectContainer db;
    private int cantidadAInsertar;

    public Db4oCreator() {
        setEngineName("db4o");
        setEngineVersion("7.7.67");
    }

    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);

        getTimer().start();
        createDatabase(getDbName());
        getTimer().stop();

        System.out.println("La BD se ha generado correctamente.\n\n");

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        List<AmenazaWrapper> testObjectsList = generateTestObjects(getCantidadAInsertar());

        getTimer().start();
        persistTestObjectsDb4o(testObjectsList);
        getTimer().stop();

        System.out.println("Registros persistidos correctamente.\n\n");

        setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void readData() {
        getTimer().start();
        readTestObjectsDb4o();
        getTimer().stop();

        System.out.println("Registros leídos correctamente.\n\n");

        setStatsReadOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset());
    }

    @Override
    public void updateData() {
        getTimer().start();
        updateTestObjectsDb4o();
        getTimer().stop();

        System.out.println("Registros actualizados correctamente.\n\n");

        setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset());
    }

    @Override
    public void deleteData() {
        getTimer().start();
        deleteTestObjectsDb4o();
        getTimer().stop();

        getDb().close();

        System.out.println("Registros eliminados correctamente.\n\n");

        setStatsDeleteOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset());
    }

    @Override
    public void dropDatabase() {

    }

    public void createDatabase(String dbName) {
        try {
            ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbName);
            setDb(db);
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    public void persistTestObjectsDb4o(List<AmenazaWrapper> list) {
        Iterator<AmenazaWrapper> listItr = list.iterator();

        try {
            while (listItr.hasNext()) {
                AmenazaWrapper testObject = listItr.next();
                getDb().store(testObject);
            }
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readTestObjectsDb4o() {
        try {
            Query query = getDb().query();
            ObjectSet<AmenazaWrapper> result = query.execute();
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }

    }

    public void updateTestObjectsDb4o() {
        try {
            Query query = getDb().query();
            query.constrain(AmenazaWrapper.class);
            ObjectSet<AmenazaWrapper> result = query.execute();
            Iterator<AmenazaWrapper> itr = result.iterator();

            while (itr.hasNext()) {
                AmenazaWrapper amenazaWrapper = itr.next();

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
        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    public void deleteTestObjectsDb4o() {
        try {
            Query query = getDb().query();
            ObjectSet<Object> result = query.execute();
            Iterator<Object> itr = result.iterator();

            while (itr.hasNext()) {
                Object testObject = itr.next();
                getDb().delete(testObject);
            }

        } catch (Db4oException e) {
            e.printStackTrace(System.err);
        }
    }

    // El método 'comprobación' lee todos los registros de la BD y los imprime en
    // pantalla
    public void comprobacion(ObjectContainer db) {
        System.out.println("\n\nCOMPROBACION\n\n");
        Query query = db.query();
        ObjectSet<Object> result = query.execute();
        Iterator<Object> itr = result.iterator();

        while (itr.hasNext()) {
            Object obj = itr.next();
            System.out.println(obj + "\n");
        }
        System.out.println("\n\nEND COMPROBACION\n\n");
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