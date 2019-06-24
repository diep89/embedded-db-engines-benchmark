package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxPerson;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
    private static BoxStore store = null;
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

    // public Box<ObjectBoxPerson> getBox(ObjectBoxPerson person, String dbName) {
    // if (store == null) {
    // try {
    // createMyObjectBox(dbName);
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
    // return store.boxFor(person.class);
    // }

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
        int edad;
        String sexo;
        int tel;
        this.cantidadAInsertar = cantidadAInsertar;

        Box<ObjectBoxPerson> box = store.boxFor(ObjectBoxPerson.class);

        for (int i = 0; i < cantidadAInsertar; i++) {
            edad = i;
            sexo = "M";
            tel = i;

            // Se indica id = 0 para que ObjectBox asigne un ID automáticamente
            box.put(new ObjectBoxPerson(0, edad, sexo, tel));
        }

        store.close();

        // long id = box.put(person);
        // // Create Person
        // person = box.get(id);
        // // Read
        // person.setLastName("Black");
        // box.put(person); // Update
        // box.remove(person); // Delete
    }

    @Override
    public void readData(String dbName, String tableName) {
        System.out.println(box.count() + " persons in ObjectBox database: ");

        for (ObjectBoxPerson p : box.getAll()) {
            System.out.println(p);
        }
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

    }

    @Override
    public void deleteData(String dbName, String tableName) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }
}
