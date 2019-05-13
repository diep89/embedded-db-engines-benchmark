package mil.ea.cideso.satac;

import org.rocksdb.RocksDB;

import java.awt.List;
import java.math.BigInteger;
import java.util.LinkedList;

import org.rocksdb.Options;
import org.rocksdb.RocksIterator;
import org.rocksdb.RocksDBException;

public class RocksDbCreator extends MotorBD {

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

    @Override
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        try {
            RocksDB db = getDb(dbName);
            // Guardo los atributos en variables locales
            setAttributesQty(attributesList.length);
            setAttributesList(attributesList);
            setAttributesType(attributesType);
            setAttributesLength(attributesLength);
            db.close();
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {
        try {
            RocksDB db = getDb(dbName);
            // System.out.println("La BD se ha generado correctamente.\n");
            for (int i = 0; i < cantidadAInsertar; i++) {
                for (int j = 0; j < getAttributesQty(); j++) {
                    if (getAttributesType()[j] == "int") {
                        // Conversión de String a byte[]
                        byte[] key = getAttributesList()[j].getBytes();
                        // Conversión de int a byte[]
                        BigInteger bigInt = BigInteger.valueOf(i);
                        byte[] value = bigInt.toByteArray();
                        // Inserción en la BD
                        getTimer().start();
                        db.put(key, value);
                        getTimer().stop();
                    } else {
                        // Conversión de String a byte[]
                        byte[] key = getAttributesList()[j].getBytes();
                        byte[] value = "M".getBytes();
                        getTimer().start();
                        db.put(key, value);
                        getTimer().stop();
                    }
                }
                System.out.print((i + 1) + " - ");
            }
            System.out.println("");
            System.out.println("");
            db.close();
            // Guardo las estadísticas de la operación.
            setStatsCreateOperation(getTimer().toString());
            // Reseteo el timer.
            setTimer(getTimer().reset());

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void dropDatabase(String dbName) {

    }

}