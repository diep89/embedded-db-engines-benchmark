package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxAmenazaWrapper;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
    private static BoxStore store = null;
    Box<ObjectBoxAmenazaWrapper> amenazaBox = null;
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
        amenazaBox = store.boxFor(ObjectBoxAmenazaWrapper.class);

        for (int i = 0; i < cantidadAInsertar; i++) {

            Tiempo tiempo = new Tiempo(1);
            Posicion posicion = new Posicion(1.5, 1.5, 1);
            Equipamiento equipamiento = new Equipamiento(1, 1, 1);
            Informante informante = new Informante("Test");
            Amenaza amenaza = new Amenaza(i, tiempo, 1, posicion, 1, 1, 1, equipamiento, informante);
            ObjectBoxAmenazaWrapper amenazaWrapper = new ObjectBoxAmenazaWrapper(0, amenaza, true, false);

            // Se indica id = 0 para que ObjectBox asigne un ID automáticamente
            amenazaBox.put(amenazaWrapper);
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
        long cantidadDeAmenazas = amenazaBox.count();
        getTimer().stop();

        System.out.println("Se encontraron " + cantidadDeAmenazas + " personas en la BD: \n");
        System.out.println("Registros leídos correctamente.\n");
        System.out.println("");

        setStatsReadOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        getTimer().start();
        List<ObjectBoxAmenazaWrapper> amenazaList = amenazaBox.getAll();
        Iterator<ObjectBoxAmenazaWrapper> amenazaListIter = amenazaList.iterator();

        int i = 0;

        while (amenazaListIter.hasNext()) {
            ObjectBoxAmenazaWrapper amenazaWrapper = amenazaListIter.next();

            Tiempo tiempo = new Tiempo(2);
            Posicion posicion = new Posicion(2.5, 2.5, 2);
            Equipamiento equipamiento = new Equipamiento(2, 2, 2);
            Informante informante = new Informante("Test2");
            Amenaza amenaza = new Amenaza(i, tiempo, 2, posicion, 2, 2, 2, equipamiento, informante);

            amenazaWrapper.setAmenaza(amenaza);
            amenazaWrapper.setLeido(true);
            amenazaBox.put(amenazaWrapper);
            i++;
        }
        getTimer().stop();

        System.out.println("Registros actualizados correctamente.");
        System.out.println("");

        setStatsUpdateOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void deleteData(String dbName, String tableName) {
        getTimer().start();
        amenazaBox.removeAll();
        store.close();
        getTimer().stop();

        System.out.println("Registros eliminados correctamente.");
        System.out.println("");

        setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void dropDatabase(String dbName) {

    }
}
