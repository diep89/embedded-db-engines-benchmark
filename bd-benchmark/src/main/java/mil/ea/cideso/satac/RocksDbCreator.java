package mil.ea.cideso.satac;

import org.rocksdb.RocksDB;
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
        RocksDB db = RocksDB.open(rockopts, "./../../../../../../../../" + dbName);
        return db;
    }

    @Override
    public void createNewDatabase(String dbName) {
        try {
            RocksDB db = getDb(dbName);
            System.out.println("La BD se ha generado correctamente.\n");

        } catch (RocksDBException e) {
            e.printStackTrace(System.err);
        }
    }

    @Override
    public void createNewTable(String dbName, String tableName, String[] attributesList, String[] attributesType,
            String[] attributesLength) {
        try {
            RocksDB db = getDb(dbName);
        } catch (RocksDBException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertData(String dbName, String tableName, int cantidadAInsertar) {

    }

    @Override
    public void dropDatabase(String dbName) {

    }

}