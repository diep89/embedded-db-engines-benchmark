package mil.ea.cideso.satac;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        // Nombre de la BD a generar.
        // Se inicializa automáticamente dentro del loop de pruebas.
        String dbName;

        // Nombre de la tabla a generar.
        String tableName = "TestTable";

        // Variables auxiliares.
        Scanner input = new Scanner(System.in);
        int cantidadAInsertar = -1;
        // String eliminar;
        int counter = 1;
        int waitMillis = 1500;

        // Instanciación de las clases creadoras de BD.
        // SqliteCreator sqlite = new SqliteCreator();
        // Db4oCreator db4o = new Db4oCreator();
        // ObjectBoxCreator objectBox = new ObjectBoxCreator();
        RocksDbCreator rocks = new RocksDbCreator();
        // H2Creator h2 = new H2Creator();
        // MongoDbCreator mongo = new MongoDbCreator();
        // RavenDbCreator raven = new RavenDbCreator();

        // Creación de lista que contiene las instancias creadas anteriormente.
        List<MotorBD> ll = new LinkedList<>();
        // ll.add(sqlite);
        // ll.add(db4o);
        // ll.add(objectBox);
        ll.add(rocks);
        // ll.add(h2);
        // ll.add(mongo);
        // ll.add(raven);

        System.out.println("Practica Profesional Supervisada");
        waitTime(waitMillis);
        System.out.println("Benchmark de motores de BD embebidos");
        waitTime(waitMillis);
        System.out.println("");
        System.out.println("Test automatizado");
        waitTime(waitMillis);
        System.out.println("");

        // Test automatizado
        System.out.println("***\n");
        System.out.println("Inicio de test automatizado\n");
        waitTime(waitMillis);
        System.out.println("Se realizarán operaciones CRUD (Create, Read, Update, Delete).\n");
        waitTime(waitMillis);
        if (cantidadAInsertar == -1) {
            System.out.print(
                    "Indique la cantidad de registros a ingresar durante las operaciones de creación (1-1000): ");
            cantidadAInsertar = input.nextInt();
            System.out.println("");
            do {
                if (cantidadAInsertar < 1 || cantidadAInsertar > 1000) {
                    System.out.print("Error: cantidad inválida. Por favor, elija una cantidad válida: ");
                    cantidadAInsertar = input.nextInt();
                    System.out.println("");
                }
            } while (cantidadAInsertar < 1 || cantidadAInsertar > 1000);
        }
        // System.out.println("");
        waitTime(waitMillis);

        // Creación de objeto 'iterator' para recorrer todas las instancias contenidas
        // en la lista.
        Iterator<MotorBD> itr = ll.iterator();

        while (itr.hasNext()) {
            MotorBD element = itr.next();
            System.out.printf("Motor %d: %s v%s\n\n", counter, element.getEngineName(), element.getEngineVersion());
            waitTime(waitMillis);
            System.out.println("1. Operación CREATE:\n");
            waitTime(waitMillis);
            dbName = "testDb" + "-" + element.getEngineName();
            element.createNewDatabase(dbName);

            waitTime(2000);

            // Creación de la tabla 'TestTable'
            // En la creación de una nueva tabla se crea automáticamente el atributo "id"
            // como PK. Input de nombre de tabla deshabilitado.
            element.createNewTable(tableName);

            // Mensaje informativo.
            // Pedido de ingreso de cantidad de registros a generar.
            System.out.println("Generación y alta de registros en la BD.");
            waitTime(waitMillis);

            System.out.println("Se ingresarán " + cantidadAInsertar + " registros en la BD.\n");
            waitTime(waitMillis);

            // Operación CREATE (Alta de registros)
            element.insertData(cantidadAInsertar);

            System.out.println("Motor: " + element.getEngineName() + "\nOperación 'CREATE' finalizada.\n");
            waitTime(waitMillis);
            pressEnter();

            System.out.println("");

            // OPERACIÓN READ
            System.out.println("2. Operación READ:\n");
            waitTime(waitMillis);
            System.out.println("Se leerán los registros ingresados en la BD.\n");
            pressEnter();
            System.out.println("");

            element.readData();

            // Mensaje informativo
            System.out.println("Motor: " + element.getEngineName() + "\nOperación 'READ' finalizada.\n");
            waitTime(waitMillis);
            pressEnter();
            System.out.println("");

            // OPERACIÓN UPDATE
            System.out.println("3. Operación UPDATE:\n");
            waitTime(waitMillis);
            System.out.println("Se actualizarán los registros ingresados en la BD con nuevos datos.\n");
            pressEnter();
            System.out.println("");

            element.updateData();
            System.out.println("Motor: " + element.getEngineName() + "\nOperación 'UPDATE' finalizada.\n");
            waitTime(waitMillis);
            pressEnter();
            System.out.println("");

            // OPERACIÓN DELETE
            System.out.println("4. Operación DELETE:\n");
            waitTime(waitMillis);
            System.out.println("Se eliminarán los registros ingresados en la BD.\n");
            pressEnter();
            System.out.println("");

            element.deleteData();
            System.out.println("Motor: " + element.getEngineName() + "\nOperación 'DELETE' finalizada.\n");
            waitTime(waitMillis);
            pressEnter();
            System.out.println("");

            // EXTRA
            // do {
            // System.out.print("¿Desea eliminar la BD (Y/N)? ");
            // eliminar = input.next();
            // if (!(eliminar.equalsIgnoreCase("y") || eliminar.equalsIgnoreCase("n"))) {
            // System.out.println("Respuesta inválida. Intente nuevamente.");
            // }
            // } while (!(eliminar.equalsIgnoreCase("y") ||
            // eliminar.equalsIgnoreCase("n")));

            // System.out.println("");

            // if (eliminar.equalsIgnoreCase("y")) {
            // File file = new File(dbName + ".db");
            // sqlite.dropDatabase(dbName + ".db");
            // if (file.delete()) {
            // System.out.println("El archivo '" + dbName + ".db' ha sido eliminado.");
            // }
            // }

            // System.out.println("");
            counter++;
        }

        // Tabla para mostrar resultados
        System.out.println("");
        System.out.println("***");
        System.out.println("");
        System.out.println("Resultados del benchmark");
        System.out.println("Operaciones CRUD (Create, Read, Update, Delete)");
        System.out.println("");

        waitTime(waitMillis);
        System.out.printf("%-15s %-15s %-15s %-15s %-15s", "Motor", "Create", "Read", "Update", "Delete");
        System.out.println("");
        System.out.println("");

        Iterator<MotorBD> itrResults = ll.iterator();
        while (itrResults.hasNext()) {
            MotorBD element = itrResults.next();
            System.out.printf("%-15s %-15s %-15s %-15s %-15s\n", element.getEngineName(),
                    element.getStatsCreateOperation(), element.getStatsReadOperation(),
                    element.getStatsUpdateOperation(), element.getStatsDeleteOperation());
        }

        System.out.println("");
        System.out.println("");

        input.close();
    }

    public static void pressEnter() {
        Scanner scanner;
        System.out.println("Presione ENTER para continuar...");
        try {
            scanner = new Scanner(System.in);
            scanner.nextLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void waitTime(int waitMilliseconds) {
        // Bloque para parar la aplicación
        try {
            Thread.sleep(waitMilliseconds);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

}
