package mil.ea.cideso.satac;

import mil.ea.cideso.satac.Db4oPerson;

import java.util.Iterator;

// import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.db4o.ext.Db4oException;
import com.db4o.query.*;
import com.db4o.types.Db4oCollection;

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
        setAttributesQty(attributesList.length);
        setAttributesList(attributesList);
        setAttributesType(attributesType);
        setAttributesLength(attributesLength);
    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {
        // this.cantidadAInsertar = cantidadAInsertar;

        try {
            getTimer().start();
            db = getDb(dbName);

            for (int i = 0; i < cantidadAInsertar; i++) {
                Db4oPerson person = new Db4oPerson(i, "M", i);
                db.store(person);
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
            query.constrain(Db4oPerson.class);
            ObjectSet<Db4oPerson> result = query.execute();
            // Db4oPerson person = (Db4oPerson) result.next();
            Iterator<Db4oPerson> db4oItr = result.iterator();

            getTimer().stop();
            int i = 1;

            System.out.println("Registros leídos:\n");
            System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");

            while (db4oItr.hasNext()) {
                Db4oPerson person = db4oItr.next();
                System.out.printf("%-10d %-10s %-10s %-10s\n", i, person.getEdad(), person.getSexo(), person.getTel());
                i++;
            }
            System.out.println("");

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
            query.constrain(Db4oPerson.class);
            ObjectSet<Db4oPerson> result = query.execute();
            Iterator<Db4oPerson> db4oItr = result.iterator();

            int i = 1;

            while (db4oItr.hasNext()) {
                Db4oPerson person = db4oItr.next();
                person.setEdad(10);
                person.setSexo("F");
                person.setTel(10);
                db.store(person);
            }

            getTimer().stop();
            db4oItr = null;
            db4oItr = result.iterator();
            System.out.println("Registros actualizados correctamente.");
            System.out.println("Lista de registros actualizada: \n");
            System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");
            while (db4oItr.hasNext()) {
                Db4oPerson person = db4oItr.next();
                System.out.printf("%-10d %-10s %-10s %-10s\n", i, person.getEdad(), person.getSexo(), person.getTel());
                i++;
            }
            System.out.println("");

            getTimer().start();
            db.close();
            getTimer().stop();

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
            query.constrain(Db4oPerson.class);
            ObjectSet<Db4oPerson> result = query.execute();
            Iterator<Db4oPerson> db4oItr = result.iterator();

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