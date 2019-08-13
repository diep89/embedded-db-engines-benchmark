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

    private int cantidadAInsertar;
    private List<String> listaClaves = new ArrayList<>();
    private List<String> listaValores = new ArrayList<>();
    private int equipamientoId = 0;

    public void generarAmenazaWrapperRocksDB(int i) {
        int id = i;
        int equipId = getEquipamientoId();

        // Genero los objetos que componen al objeto AmenazaWrapper
        Informante informante = new Informante();
        informante.setId(id);

        double latitud = 1.5;
        double longitud = 1.5;
        int milisegundosFechaHora = 1;
        Posicion posicion = new Posicion(latitud, longitud, milisegundosFechaHora);
        posicion.setId(id);

        int epoch = 1;
        Tiempo tiempo = new Tiempo(epoch);
        tiempo.setId(id);

        int cantidad = 1;
        int equipo = 1;
        int tipo = 1;
        Equipamiento equipamiento = new Equipamiento(cantidad, equipo, tipo);
        Equipamiento equipamiento2 = new Equipamiento(cantidad, equipo, tipo);
        Equipamiento equipamiento3 = new Equipamiento(cantidad, equipo, tipo);
        equipamiento.setId(equipId);
        equipId += 1;
        equipamiento2.setId(equipId);
        equipId += 1;
        equipamiento3.setId(equipId);
        equipId += 1;
        setEquipamientoId(equipId);

        List<Equipamiento> equipamientoList = new ArrayList<>();
        equipamientoList.add(equipamiento);
        equipamientoList.add(equipamiento2);
        equipamientoList.add(equipamiento3);

        int codigoSimbolo = 1;
        int radioAccion = 1;
        int identificacion = 1;
        int tamanios = 1;
        Amenaza amenaza = new Amenaza(tiempo, codigoSimbolo, posicion, radioAccion, identificacion, tamanios,
                equipamientoList, informante);
        amenaza.setId(id);

        boolean visible = true;
        boolean leido = false;
        AmenazaWrapper amenazaWrapper = new AmenazaWrapper(id, amenaza, visible, leido);

        // Limpio las listas a cargar.
        getListaClaves().clear();
        getListaValores().clear();

        // Lleno las listas 'listaClaves' y 'listaValores' (de tipo byte[]) con los
        // atributos de todos los objetos.
        // Tanto las claves como los valores se convierten de su tipo original a String
        // y son almacenadas en la lista correspondiente.

        getListaClaves().add("leido");
        getListaValores().add(String.valueOf(amenazaWrapper.isLeido()));
        getListaClaves().add("visible");
        getListaValores().add(String.valueOf(amenazaWrapper.isVisible()));

        getListaClaves().add("codigoSimbolo");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getCodigoSimbolo()));
        getListaClaves().add("radioAccion");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getRadioAccion()));
        getListaClaves().add("identificacion");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getIdentificacion()));
        getListaClaves().add("tamanios");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getTamanios()));

        Iterator<Equipamiento> equipItr = amenazaWrapper.getAmenaza().getEquipamientoList().iterator();
        while (equipItr.hasNext()) {
            Equipamiento equip = equipItr.next();
            getListaClaves().add("cantidad" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getCantidad()));
            getListaClaves().add("equipo" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getEquipo()));
            getListaClaves().add("tipo" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getTipo()));
        }

        getListaClaves().add("epoch");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getTiempo().getEpoch()));

        getListaClaves().add("latitud");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLatitud()));
        getListaClaves().add("longitud");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLongitud()));
        getListaClaves().add("milisegundosFechaHora");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getMilisegundosFechaHora()));

    }

    public void updateAmenazaWrapperRocksDB(int i) {
        int id = i;
        int equipId = getEquipamientoId();

        // Genero los objetos que componen al objeto AmenazaWrapper
        Informante informante = new Informante();
        informante.setId(id);

        double latitud = 2.5;
        double longitud = 2.5;
        int milisegundosFechaHora = 2;
        Posicion posicion = new Posicion(latitud, longitud, milisegundosFechaHora);
        posicion.setId(id);

        int epoch = 2;
        Tiempo tiempo = new Tiempo(epoch);
        tiempo.setId(id);

        int cantidad = 2;
        int equipo = 2;
        int tipo = 2;
        Equipamiento equipamiento = new Equipamiento(cantidad, equipo, tipo);
        Equipamiento equipamiento2 = new Equipamiento(cantidad, equipo, tipo);
        Equipamiento equipamiento3 = new Equipamiento(cantidad, equipo, tipo);
        equipamiento.setId(equipId);
        equipId += 1;
        equipamiento2.setId(equipId);
        equipId += 1;
        equipamiento3.setId(equipId);
        equipId += 1;
        setEquipamientoId(equipId);

        List<Equipamiento> equipamientoList = new ArrayList<>();
        equipamientoList.add(equipamiento);
        equipamientoList.add(equipamiento2);
        equipamientoList.add(equipamiento3);

        int codigoSimbolo = 2;
        int radioAccion = 2;
        int identificacion = 2;
        int tamanios = 2;
        Amenaza amenaza = new Amenaza(tiempo, codigoSimbolo, posicion, radioAccion, identificacion, tamanios,
                equipamientoList, informante);

        boolean visible = true;
        boolean leido = true;
        AmenazaWrapper amenazaWrapper = new AmenazaWrapper(id, amenaza, visible, leido);

        // Lleno las listas 'listaClaves' y 'listaValores' (de tipo byte[]) con los
        // atributos de todos los objetos.
        getListaClaves().clear();
        getListaValores().clear();

        // Tanto las claves como los valores se convierten de su tipo original a String
        // y luego a byte[].
        getListaClaves().add("leido");
        getListaValores().add(String.valueOf(amenazaWrapper.isLeido()));
        getListaClaves().add("visible");
        getListaValores().add(String.valueOf(amenazaWrapper.isVisible()));

        getListaClaves().add("codigoSimbolo");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getCodigoSimbolo()));
        getListaClaves().add("radioAccion");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getRadioAccion()));
        getListaClaves().add("identificacion");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getIdentificacion()));
        getListaClaves().add("tamanios");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getTamanios()));

        Iterator<Equipamiento> equipItr = amenazaWrapper.getAmenaza().getEquipamientoList().iterator();
        while (equipItr.hasNext()) {
            Equipamiento equip = equipItr.next();
            getListaClaves().add("cantidad" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getCantidad()));
            getListaClaves().add("equipo" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getEquipo()));
            getListaClaves().add("tipo" + "_" + equip.getId());
            getListaValores().add(String.valueOf(equip.getTipo()));
        }

        getListaClaves().add("epoch");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getTiempo().getEpoch()));

        getListaClaves().add("latitud");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLatitud()));
        getListaClaves().add("longitud");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getLongitud()));
        getListaClaves().add("milisegundosFechaHora");
        getListaValores().add(String.valueOf(amenazaWrapper.getAmenaza().getPosicion().getMilisegundosFechaHora()));
    }

    public RocksDbCreator() {
        setEngineName("RocksDB");
        setEngineVersion("6.0.1");
    }

    public RocksDB getDb(String dbName) throws RocksDBException {
        RocksDB.loadLibrary();
        Options rockopts = new Options().setCreateIfMissing(true);
        RocksDB db = RocksDB.open(rockopts, dbName);
        return db;
    }

    @Override
    public void createNewDatabase(String dbName) {
        setDbName(dbName);

        try {
            getTimer().start();
            RocksDB db = getDb(getDbName());
            getTimer().stop();
            System.out.println("La BD se ha generado correctamente.\n");
            db.close();
            setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset()); // Reseteo el timer.
        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void insertData(int cantidadAInsertar) {
        setCantidadAInsertar(cantidadAInsertar);

        try {
            getTimer().start();
            RocksDB db = getDb(getDbName());

            // En cada iteración genero un objeto AmenazaWrapper e inserto
            // sus atributos en la BD.
            for (int i = 0; i < getCantidadAInsertar(); i++) {

                generarAmenazaWrapperRocksDB(i);

                Iterator<String> itrClaves = getListaClaves().iterator();
                Iterator<String> itrValores = getListaValores().iterator();

                WriteBatch batch = new WriteBatch();
                WriteOptions writeOpts = new WriteOptions();

                while (itrClaves.hasNext() && itrValores.hasNext()) {
                    byte[] key = new String(itrClaves.next() + "_" + i).getBytes();
                    byte[] value = new String(itrValores.next()).getBytes();
                    batch.put(key, value);
                }

                db.write(writeOpts, batch);

                // getTimer().stop();
                // if (i + 1 < getCantidadAInsertar()) {
                // System.out.print((i + 1) + " - ");
                // } else {
                // System.out.print((i + 1) + ".");
                // }
                // getTimer().start();
            }
            db.close();
            getTimer().stop();

            System.out.println("");
            System.out.println("");
            setStatsInsertOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset()); // Reseteo el timer.

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void readData() {
        try {
            getTimer().start();
            RocksDB db = getDb(getDbName());
            RocksIterator rocksIter = db.newIterator();
            getTimer().stop();

            // int i = 1;
            getTimer().start();
            for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                String key = new String(rocksIter.key(), StandardCharsets.UTF_8);
                String val = new String(rocksIter.value(), StandardCharsets.UTF_8);
                // getTimer().stop();
                // Habilitar para mostrar en pantalla el resultado de la consulta
                // System.out.printf("%-5d Clave: %-15s Valor: %-15s\n", i, key, val);
                // i++;
                // getTimer().start();
            }
            rocksIter.close();
            db.close();
            getTimer().stop();

            System.out.println("");
            setStatsReadOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    @Override
    public void updateData() {
        setEquipamientoId(0);
        try {
            getTimer().start();
            RocksDB db = getDb(getDbName());
            RocksIterator rocksIter = db.newIterator();

            for (int i = 0; i < getCantidadAInsertar(); i++) {

                updateAmenazaWrapperRocksDB(i);
                Iterator<String> itrClaves = getListaClaves().iterator();
                Iterator<String> itrValores = getListaValores().iterator();

                while (itrClaves.hasNext() && itrValores.hasNext()) {
                    String keyToFindString = new String(itrClaves.next() + "_" + i);
                    byte[] keyToFind = keyToFindString.getBytes();
                    byte[] currentValue = db.get(keyToFind);
                    byte[] newValue = itrValores.next().getBytes();
                    for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                        if (currentValue != null) { // currentValue == null if key1 does not exist in db.
                            if (currentValue != newValue) {
                                db.delete(keyToFind);
                                db.put(new String(keyToFindString).getBytes(), newValue);
                            }
                        }
                    }
                }
            }

            getTimer().stop();

            System.out.println("Registros actualizados correctamente.");
            // System.out.println("Lista de registros actualizada: \n");
            // int i = 0;
            // RocksIterator rocksIter2 = db.newIterator();
            // for (rocksIter2.seekToFirst(); rocksIter2.isValid(); rocksIter2.next()) {
            // String key = new String(rocksIter2.key(), StandardCharsets.UTF_8);
            // String val = new String(rocksIter2.value(), StandardCharsets.UTF_8);
            // System.out.printf("%-5d Clave: %-15s Valor: %-15s\n", i, key, val);
            // i++;
            // }
            getTimer().start();
            rocksIter.close();
            db.close();
            getTimer().stop();

            System.out.println("");
            setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (

        RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    @Override
    public void deleteData() {
        try {
            getTimer().start();
            RocksDB db = getDb(getDbName());
            RocksIterator rocksIter = db.newIterator();

            for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                db.delete(rocksIter.key());
            }

            db.close();
            getTimer().stop();

            System.out.println("Registros eliminados correctamente.");
            System.out.println("");
            setStatsDeleteOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (

        RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    @Override
    public void dropDatabase() {

    }

    public void setListaClaves(List<String> listaClaves) {
        this.listaClaves = listaClaves;
    }

    public void setListaValores(List<String> listaValores) {
        this.listaValores = listaValores;
    }

    public List<String> getListaClaves() {
        return listaClaves;
    }

    public List<String> getListaValores() {
        return listaValores;
    }

    public int getCantidadAInsertar() {
        return cantidadAInsertar;
    }

    public void setCantidadAInsertar(int cantidadAInsertar) {
        this.cantidadAInsertar = cantidadAInsertar;
    }

    public int getEquipamientoId() {
        return equipamientoId;
    }

    public void setEquipamientoId(int equipamientoId) {
        this.equipamientoId = equipamientoId;
    }

    // Métodos auxiliares
    // private Byte convertByteArrayToInt(byte[] intBytes) {
    // // ByteBuffer byteBuffer = ByteBuffer.wrap(intBytes);
    // int capacity = 1;
    // ByteBuffer bb = ByteBuffer.allocate(capacity);
    // bb.put(intBytes);
    // bb.rewind();
    // return bb.get();
    // }

}