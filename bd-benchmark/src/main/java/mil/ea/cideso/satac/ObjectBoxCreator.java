package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxPerson;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
    BoxStore person = null;
    int cantidadAInsertar;

    public ObjectBoxCreator() {
        setEngineName("ObjectBox");
        setEngineVersion("2.3.4");
    }

    @Override
    public void createNewDatabase(String dbName) {
        BoxStore person = MyObjectBox.builder().name(dbName).build();
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

        Box<ObjectBoxPerson> box = person.boxFor(ObjectBoxPerson.class);

        for (int i = 0; i < cantidadAInsertar; i++) {
            edad = i;
            sexo = "M";
            tel = i;

            box.put(new ObjectBoxPerson(i, edad, sexo, tel));
        }

        System.out.println(box.count() + " persons in ObjectBox database: ");

        for (ObjectBoxPerson p : box.getAll()) {
            System.out.println(p);
        }

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

    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {

    }

    @Override
    public void deleteData(String dbName, String tableName) {
        person.close();
    }

    @Override
    public void dropDatabase(String dbName) {

    }
}
