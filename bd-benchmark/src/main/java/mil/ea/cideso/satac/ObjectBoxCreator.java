package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxPerson;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
    private static BoxStore store = null;
    Box<ObjectBoxPerson> personsBox = null;
    int cantidadAInsertar;

    public ObjectBoxCreator() {
        setEngineName("ObjectBox");
        setEngineVersion("2.3.4");
    }

    private void createMyObjectBox(String dbName) throws IOException {
        if (store == null) {
            store = MyObjectBox.builder().name(dbName).build();
        }
    }

    @Override
    public void createNewDatabase(String dbName) {
        try {
            createMyObjectBox(dbName);
        } catch (IOException e) {
            e.printStackTrace();
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
        this.cantidadAInsertar = cantidadAInsertar;

        getTimer().start();
        personsBox = store.boxFor(ObjectBoxPerson.class);

        for (int i = 0; i < cantidadAInsertar; i++) {

            // Se indica id = 0 para que ObjectBox asigne un ID automáticamente
            personsBox.put(new ObjectBoxPerson(0, edad, sexo, tel));
            getTimer().stop();

            if (i + 1 < cantidadAInsertar) {
                System.out.print((i + 1) + " - ");
            } else {
                System.out.print((i + 1) + ".");
            }
            getTimer().start();
        }
        getTimer().stop();

        System.out.println("");
        System.out.println("");

        setStatsCreateOperation(getTimer().toString()); // Guardo el timer para operación CREATE.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void readData(String dbName, String tableName) {
        getTimer().start();
        long cantidadDePersonas = personsBox.count();
        List<ObjectBoxPerson> personsList = personsBox.getAll();
        Iterator<ObjectBoxPerson> personsListIter = personsList.iterator();
        getTimer().stop();

        System.out.println("Se encontraron " + cantidadDePersonas + " personas en la BD: \n");
        System.out.println("Registros leídos:\n");
        System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");

        while (personsListIter.hasNext()) {
            ObjectBoxPerson person = personsListIter.next();
            System.out.printf("%-10d %-10s %-10s %-10s\n", person.getId(), person.getEdad(), person.getSexo(),
                    person.getTel());
        }

        System.out.println("");

        setStatsReadOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        getTimer().start();
        List<ObjectBoxPerson> personsList = personsBox.getAll();
        Iterator<ObjectBoxPerson> personsListIter = personsList.iterator();

        while (personsListIter.hasNext()) {
            ObjectBoxPerson person = personsListIter.next();
            person.setEdad(10);
            person.setSexo("M");
            person.setTel(10);
            personsBox.put(person);
        }
        getTimer().stop();

        personsListIter = null;
        personsList = personsBox.getAll();
        personsListIter = personsList.iterator();

        System.out.println("Registros actualizados correctamente.");
        System.out.println("Lista de registros actualizada: \n");
        System.out.printf("%-10s %-10s %-10s %-10s\n", "Id", "Edad", "Sexo", "Telefono");
        while (personsListIter.hasNext()) {
            ObjectBoxPerson person = personsListIter.next();
            System.out.printf("%-10d %-10s %-10s %-10s\n", person.getId(), person.getEdad(), person.getSexo(),
                    person.getTel());
        }

        System.out.println("");

        setStatsUpdateOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void deleteData(String dbName, String tableName) {
        getTimer().start();
        personsBox.removeAll();
        store.close();
        getTimer().stop();

        System.out.println("Registros eliminados correctamente.\n");

        setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void dropDatabase(String dbName) {

    }
}
