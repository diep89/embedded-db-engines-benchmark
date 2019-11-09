package mil.ea.cideso.satac;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.rocksdb.RocksDB;
import org.rocksdb.Options;
import org.rocksdb.RocksIterator;
import org.rocksdb.WriteBatch;
import org.rocksdb.WriteOptions;
import org.rocksdb.RocksDBException;

public class RocksDbCreator extends MotorBD {

    private RocksDB db;

    private List<String> keysList = new ArrayList<>();
    private List<String> valuesList = new ArrayList<>();

    private int cantidadAInsertar;

    public RocksDbCreator() {
        setEngineName("RocksDB");
        setEngineVersion("6.0.1");
    }

    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);

        getTimer().start();
        createDatabase(getDbName());
        getTimer().stop();

        // getDb().close();

        System.out.println("La BD se ha generado correctamente.\n");

        setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        List<AmenazaWrapper> testObjectsList = generateTestObjects(getCantidadAInsertar());
        loadLists(testObjectsList);

        getTimer().start();
        persistTestObjectsRocksDB();
        getTimer().stop();

        // comprobacion();

        System.out.println("Registros persistidos correctamente.\n\n");

        setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset()); // Reseteo el timer.
    }

    @Override
    public void readData() {
        getTimer().start();
        readTestObjectsRocksDB();
        getTimer().stop();

        System.out.println("Registros leídos correctamente.\n\n");

        setStatsReadOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset());
    }

    @Override
    public void updateData() {
        List<AmenazaWrapper> testObjectsList = generateTestObjects(getCantidadAInsertar());
        List<AmenazaWrapper> updatedObjects = updateNewObjects(testObjectsList);
        clearLists();
        loadLists(updatedObjects);

        getTimer().start();
        updateTestObjectsRocksDB();
        getTimer().stop();

        // comprobacion();

        System.out.println("Registros actualizados correctamente.\n\n");

        setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
        setTimer(getTimer().reset());
    }

    @Override
    public void deleteData() {
        getTimer().start();
        deleteTestObjectsRocksDB();
        getTimer().stop();

        // comprobacion();

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
            RocksDB.loadLibrary();
            Options rockopts = new Options().setCreateIfMissing(true);
            RocksDB db = RocksDB.open(rockopts, dbName);
            setDb(db);
        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    public void loadLists(List<AmenazaWrapper> list) {
        int id = 0;
        int equipId = 0;
        Iterator<AmenazaWrapper> listItr = list.iterator();

        while (listItr.hasNext()) {
            AmenazaWrapper amenazaWrapper = listItr.next();

            getKeysList().add("leido" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.isLeido()));
            getKeysList().add("visible" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.isVisible()));

            getKeysList().add("codigoSimbolo" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getCodigoSimbolo()));
            getKeysList().add("radioAccion" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getRadioAccion()));
            getKeysList().add("identificacion" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getIdentificacion()));
            getKeysList().add("tamanios" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getTamanios()));

            Iterator<Equipamiento> equipItr = amenazaWrapper.getAmenaza().getEquipamientoList().iterator();
            while (equipItr.hasNext()) {
                Equipamiento equip = equipItr.next();
                getKeysList().add("cantidad" + "_" + equipId);
                getValuesList().add(String.valueOf(equip.getCantidad()));
                getKeysList().add("equipo" + "_" + equipId);
                getValuesList().add(String.valueOf(equip.getEquipo()));
                getKeysList().add("tipo" + "_" + equipId);
                getValuesList().add(String.valueOf(equip.getTipo()));
                equipId++;
            }

            getKeysList().add("epoch" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getTiempo().getEpoch()));

            getKeysList().add("latitud" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLatitud()));
            getKeysList().add("longitud" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLongitud()));
            getKeysList().add("milisegundosFechaHora" + "_" + id);
            getValuesList().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getMilisegundosFechaHora()));

            id++;
        }
    }

    public void clearLists() {
        getKeysList().clear();
        getValuesList().clear();
    }

    // 'persistTestObjectsRocksDB()' recibe una lista con todos los objetos
    // AmenazaWrapper que se deben persistir.
    public void persistTestObjectsRocksDB() {
        Iterator<String> keysItr = getKeysList().iterator();
        Iterator<String> valuesItr = getValuesList().iterator();

        WriteBatch batch = new WriteBatch();
        WriteOptions writeOpts = new WriteOptions();

        try {

            while (keysItr.hasNext() && valuesItr.hasNext()) {
                byte[] key = keysItr.next().getBytes();
                byte[] value = valuesItr.next().getBytes();
                batch.put(key, value);
            }

            db.write(writeOpts, batch);

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    public void readTestObjectsRocksDB() {
        RocksIterator rocksIter = getDb().newIterator();

        for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
            String key = new String(rocksIter.key(), StandardCharsets.UTF_8);
            String val = new String(rocksIter.value(), StandardCharsets.UTF_8);
        }
        rocksIter.close();
    }

    public void updateTestObjectsRocksDB() {
        Iterator<String> keysItr = getKeysList().iterator();
        Iterator<String> valuesItr = getValuesList().iterator();

        try {
            while (keysItr.hasNext() && valuesItr.hasNext()) {
                byte[] keyToFind = keysItr.next().getBytes();
                byte[] currentValue = db.get(keyToFind);
                byte[] newValue = valuesItr.next().getBytes();

                if (currentValue != null && currentValue != newValue) {
                    db.delete(keyToFind);
                    db.put(keyToFind, newValue);
                }
            }
        } catch (RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    public List<AmenazaWrapper> updateNewObjects(List<AmenazaWrapper> list) {
        Iterator<AmenazaWrapper> listItr = list.iterator();

        while (listItr.hasNext()) {
            AmenazaWrapper amenazaWrapper = listItr.next();

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

        return list;
    }

    public void deleteTestObjectsRocksDB() {
        try {
            RocksIterator rocksIter = getDb().newIterator();

            for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                db.delete(rocksIter.key());
            }

        } catch (RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    public void comprobacion() {
        int i = 0;
        RocksIterator rocksIter = getDb().newIterator();

        System.out.println("\n\nCOMPROBACION\n\n");
        for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
            String key = new String(rocksIter.key(), StandardCharsets.UTF_8);
            String val = new String(rocksIter.value(), StandardCharsets.UTF_8);

            System.out.printf("%-5d Clave: %-15s Valor: %-15s\n", i, key, val);
            i++;
        }
        System.out.println("\n\nEND COMPROBACION\n\n");

        rocksIter.close();
    }

    public void setListaClaves(List<String> listaClaves) {
        this.keysList = listaClaves;
    }

    public void setListaValores(List<String> listaValores) {
        this.valuesList = listaValores;
    }

    public List<String> getListaClaves() {
        return keysList;
    }

    public List<String> getListaValores() {
        return valuesList;
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }

    public RocksDB getDb() {
        return db;
    }

    public void setDb(RocksDB db) {
        this.db = db;
    }

    public List<String> getKeysList() {
        return keysList;
    }

    public void setKeysList(List<String> keysList) {
        this.keysList = keysList;
    }

    public List<String> getValuesList() {
        return valuesList;
    }

    public void setValuesList(List<String> valuesList) {
        this.valuesList = valuesList;
    }

}