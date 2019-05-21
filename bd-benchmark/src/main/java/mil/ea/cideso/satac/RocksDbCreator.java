package mil.ea.cideso.satac;

import org.rocksdb.RocksDB;

import java.awt.List;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import org.rocksdb.Options;
import org.rocksdb.RocksIterator;
import org.rocksdb.RocksDBException;

public class RocksDbCreator extends MotorBD {

    int cantidadAInsertar;

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
        try {
            RocksDB db = getDb(dbName);
            System.out.println("La BD se ha generado correctamente.\n");
            db.close();

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    // En esta función no se crea una tabla nueva.
    // Solamente se guardan los atributos para luego hacer la inserción de datos.
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

        try {
            getTimer().start();
            RocksDB db = getDb(dbName);

            for (int i = 0; i < cantidadAInsertar; i++) {
                for (int j = 0; j < getAttributesQty(); j++) {
                    // Preparación de la clave a insertar
                    byte[] key = new String(getAttributesList()[j] + i).getBytes();
                    if (getAttributesType()[j] == "int") {
                        // Preparación del valor asociado a la clave a insertar
                        // cuando el mismo es de tipo 'int'
                        byte[] value = String.valueOf(i).getBytes();
                        // Inserción en la BD
                        db.put(key, value);
                    } else {
                        // Preparación del valor asociado a la clave a insertar
                        // cuando la misma es de tipo 'varchar'
                        byte[] value = "M".getBytes();
                        db.put(key, value);
                    }
                }
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

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void readData(String dbName, String tableName) {
        try {
            getTimer().start();
            RocksDB db = getDb(dbName);
            RocksIterator rocksIter = db.newIterator();
            getTimer().stop();

            int i = 1;
            getTimer().start();
            for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                String key = new String(rocksIter.key(), StandardCharsets.UTF_8);
                String val = new String(rocksIter.value(), StandardCharsets.UTF_8);
                getTimer().stop();
                System.out.printf("%-5d Clave: %-15s Valor: %-15s\n", i, key, val);
                i++;
                getTimer().start();
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
    public void updateData(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        try {
            getTimer().start();
            RocksDB db = getDb(dbName);
            for (int i = 0; i < cantidadAInsertar; i++) {
                for (int j = 0; j < getAttributesQty(); j++) {
                    if (getAttributesType()[j] == "int") {
                        db.put(new String(getAttributesList()[j] + i).getBytes(), String.valueOf(10).getBytes());
                    } else {
                        db.put(new String(getAttributesList()[j] + i).getBytes(), "F".getBytes());
                    }
                }
            }
            getTimer().stop();

            System.out.println("Registros actualizados correctamente.");
            System.out.println("Lista de registros actualizada: \n");
            int i = 1;
            RocksIterator rocksIter = db.newIterator();
            for (rocksIter.seekToFirst(); rocksIter.isValid(); rocksIter.next()) {
                String key = new String(rocksIter.key(), StandardCharsets.UTF_8);
                String val = new String(rocksIter.value(), StandardCharsets.UTF_8);
                System.out.printf("%-5d Clave: %-15s Valor: %-15s\n", i, key, val);
                i++;
            }
            rocksIter.close();
            getTimer().start();
            db.close();
            getTimer().stop();

            System.out.println("");
            setStatsUpdateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    @Override
    public void deleteData(String dbName, String tableName) {
        try {
            getTimer().start();
            RocksDB db = getDb(dbName);
            for (int i = 0; i < cantidadAInsertar; i++) {
                for (int j = 0; j < getAttributesQty(); j++) {
                    db.delete(new String(getAttributesList()[j] + i).getBytes());
                }
            }
            db.close();
            getTimer().stop();

            System.out.println("Registros eliminados correctamente.");
            System.out.println("");
            setStatsDeleteOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
            setTimer(getTimer().reset());
        } catch (RocksDBException rdbe) {
            rdbe.printStackTrace(System.err);
        }
    }

    @Override
    public void dropDatabase(String dbName) {

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