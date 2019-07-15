package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxAmenazaWrapper;
import mil.ea.cideso.satac.ObjectBoxAmenaza;
import mil.ea.cideso.satac.ObjectBoxTiempo;
import mil.ea.cideso.satac.ObjectBoxPosicion;
import mil.ea.cideso.satac.ObjectBoxEquipamiento;
import mil.ea.cideso.satac.ObjectBoxInformante;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
    private static BoxStore store = null;
    Box<ObjectBoxTiempo> tiempoBox = null;
    Box<ObjectBoxPosicion> posicionBox = null;
    Box<ObjectBoxEquipamiento> equipamientoBox = null;
    Box<ObjectBoxInformante> informanteBox = null;
    Box<ObjectBoxAmenaza> amenazaBox = null;
    Box<ObjectBoxAmenazaWrapper> amenazaWrapperBox = null;
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
        tiempoBox = store.boxFor(ObjectBoxTiempo.class);
        posicionBox = store.boxFor(ObjectBoxPosicion.class);
        equipamientoBox = store.boxFor(ObjectBoxEquipamiento.class);
        informanteBox = store.boxFor(ObjectBoxInformante.class);
        amenazaBox = store.boxFor(ObjectBoxAmenaza.class);
        amenazaWrapperBox = store.boxFor(ObjectBoxAmenazaWrapper.class);

        for (int i = 0; i < cantidadAInsertar; i++) {

            // Se indica id = 0 para que ObjectBox asigne un ID automáticamente
            ObjectBoxTiempo tiempo = new ObjectBoxTiempo(0, 1, 0);
            ObjectBoxPosicion posicion = new ObjectBoxPosicion(0, 1.5, 1.5, 1, 0);
            ObjectBoxEquipamiento equipamiento = new ObjectBoxEquipamiento(0, 1, 1, 1, 0);
            ObjectBoxInformante informante = new ObjectBoxInformante(0, 0);
            ObjectBoxAmenaza amenaza = new ObjectBoxAmenaza(i, 1, 1, 1, 1, 0);
            ObjectBoxAmenazaWrapper amenazaWrapper = new ObjectBoxAmenazaWrapper(0, true, false);

            // Declaración de relaciones entre objetos:
            tiempo.amenaza.setTarget(amenaza);
            posicion.amenaza.setTarget(amenaza);
            equipamiento.amenaza.setTarget(amenaza);
            informante.amenaza.setTarget(amenaza);
            amenaza.amenazaWrapper.setTarget(amenazaWrapper);

            tiempoBox.put(tiempo);
            posicionBox.put(posicion);
            equipamientoBox.put(equipamiento);
            informanteBox.put(informante);
            amenazaBox.put(amenaza);
            amenazaWrapperBox.put(amenazaWrapper);
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

        System.out.println("Registros leídos correctamente.\n");
        System.out.println("Se encontraron " + cantidadDeAmenazas + " amenazas guardadas en la BD.\n");
        System.out.println("");

        setStatsReadOperation(getTimer().toString()); // Guardo el timer para operación READ.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        getTimer().start();
        List<ObjectBoxAmenazaWrapper> amenazaWrapperList = amenazaWrapperBox.getAll();
        Iterator<ObjectBoxAmenazaWrapper> amenazaListIter = amenazaWrapperList.iterator();

        int i = 0;

        while (amenazaListIter.hasNext()) {
            ObjectBoxAmenazaWrapper amenazaWrapper = amenazaListIter.next();

            ObjectBoxTiempo tiempo = new ObjectBoxTiempo(0, 2, 0);
            ObjectBoxPosicion posicion = new ObjectBoxPosicion(0, 2.5, 2.5, 2, 0);
            ObjectBoxEquipamiento equipamiento = new ObjectBoxEquipamiento(0, 2, 2, 2, 0);
            ObjectBoxInformante informante = new ObjectBoxInformante(0, 0);
            ObjectBoxAmenaza amenaza = new ObjectBoxAmenaza(i, 2, 2, 2, 2, 0);
            amenazaWrapper.setLeido(true);

            tiempo.amenaza.setTarget(amenaza);
            posicion.amenaza.setTarget(amenaza);
            equipamiento.amenaza.setTarget(amenaza);
            informante.amenaza.setTarget(amenaza);
            amenaza.amenazaWrapper.setTarget(amenazaWrapper);

            tiempoBox.put(tiempo);
            posicionBox.put(posicion);
            equipamientoBox.put(equipamiento);
            informanteBox.put(informante);
            amenazaBox.put(amenaza);
            amenazaWrapperBox.put(amenazaWrapper);
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
