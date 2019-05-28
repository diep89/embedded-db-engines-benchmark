package mil.ea.cideso.satac;

// import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int menuOption;

        // Nombre de la BD a generar.
        // Se inicializa dentro del loop de pruebas.
        String dbName;

        // Nombre de la tabla a generar.
        String tableName = "TestTable";

        // Atributos definidos para el test automatizado.
        // Solo se aceptan los tipos de archivo 'int' y 'varchar'
        String attributesList[] = { "Edad", "Sexo", "Telefono" };
        String attributesType[] = { "int", "varchar", "int" };
        String attributesLength[] = { "2", "1", "8" };

        // Variables auxiliares.
        Scanner input = new Scanner(System.in);
        int cantidadAInsertar = -1;
        // String eliminar;
        int counter = 1;
        int waitMillis = 1500;

        // Instanciación de las clases creadoras de BD.
        // SqliteCreator sqlite = new SqliteCreator();
        // MongoDbCreator mongo = new MongoDbCreator();
        // RocksDbCreator rocks = new RocksDbCreator();
        // RavenDbCreator raven = new RavenDbCreator();
        // ObjectBoxCreator objectBox = new ObjectBoxCreator();
        Db4oCreator db4o = new Db4oCreator();

        // Creación de lista que contiene las instancias creadas anteriormente.
        LinkedList<MotorBD> ll = new LinkedList<MotorBD>();
        // ll.add(sqlite);
        // ll.add(mongo);
        // ll.add(rocks);
        // ll.add(objectBox);
        ll.add(db4o);

        System.out.println("Practica Profesional Supervisada");
        waitTime(waitMillis);
        System.out.println("Benchmark de motores de BD embebidos");
        waitTime(waitMillis);
        System.out.println("");

        do {
            System.out.println("Opciones:");
            waitTime(waitMillis);
            System.out.println("1 - Test automatizado");
            System.out.println("2 - Test con ingreso manual de atributos");
            waitTime(2500);
            System.out.println("");
            System.out.print("Por favor, elija una opción (0 para finalizar): ");
            menuOption = input.nextInt();
            System.out.println("");
            do {
                if (menuOption < 0 || menuOption > 2) {
                    System.out.print("Error: Opción inválida. Por favor, elija una opción válida (0 para finalizar): ");
                    menuOption = input.nextInt();
                    System.out.println("");
                }
            } while (menuOption < 0 || menuOption > 2);

            switch (menuOption) {
            // Test automatizado
            case 1:
                System.out.println("***\n");
                System.out.println("Inicio de test automatizado\n");
                waitTime(waitMillis);
                System.out.println("Se realizarán operaciones CRUD (Create, Read, Update, Delete).\n");
                waitTime(waitMillis);
                if (cantidadAInsertar == -1) {
                    System.out.print(
                            "Indique la cantidad de registros a ingresar durante las operaciones de creación (1-100): ");
                    cantidadAInsertar = input.nextInt();
                    System.out.println("");
                    do {
                        if (cantidadAInsertar < 1 || cantidadAInsertar > 100) {
                            System.out.print("Error: cantidad inválida. Por favor, elija una cantidad válida: ");
                            cantidadAInsertar = input.nextInt();
                            System.out.println("");
                        }
                    } while (cantidadAInsertar < 1 || cantidadAInsertar > 100);
                }
                // System.out.println("");
                waitTime(waitMillis);

                // Creación de objeto 'iterator' para recorrer todas las instancias contenidas
                // en la lista.
                Iterator<MotorBD> itr = ll.iterator();
                while (itr.hasNext()) {
                    MotorBD element = itr.next();
                    System.out.printf("Motor %d: %s v%s\n\n", counter, element.getEngineName(),
                            element.getEngineVersion());
                    waitTime(waitMillis);
                    System.out.println("1. Operación CREATE:\n");
                    waitTime(waitMillis);
                    dbName = "testDb" + "-" + element.getEngineName();
                    element.createNewDatabase(dbName);

                    switch (element.getEngineName().toLowerCase()) {
                    case "sqlite":
                        // Mensaje informativo sobre atributos.
                        System.out.println("Se procede a realizar la creación de la tabla de prueba en la BD.");
                        waitTime(waitMillis);
                        System.out.println("Los atributos que se crearán son: ");
                        waitTime(waitMillis);
                        for (int j = 0; j < attributesList.length; j++) {
                            System.out.printf("%-10s " + attributesType[j] + "(" + attributesLength[j] + ")\n",
                                    attributesList[j]);
                            waitTime(1000);
                        }
                        System.out.println("");
                        break;
                    case "objectBox":
                    case "db4o":
                        // Mensaje informativo sobre atributos.
                        System.out.println("Se procede a realizar la creación de la BD.");
                        waitTime(waitMillis);
                        System.out.println(
                                "Para realizar una comparativa correcta, se realizará la inserción de registros que contengan el siguiente formato de atributos: ");
                        waitTime(waitMillis);
                        for (int j = 0; j < attributesList.length; j++) {
                            System.out.printf("%-10s " + attributesType[j] + "(" + attributesLength[j] + ")\n",
                                    attributesList[j]);
                            waitTime(1000);
                        }
                        System.out.println("");
                        break;
                    case "mongo":
                        // Mensaje informativo sobre atributos.
                        System.out.println("Se procede a realizar la creación de la colección de prueba en la BD.");
                        waitTime(waitMillis);
                        System.out.println(
                                "Para realizar una comparativa correcta, se realizará la inserción de documentos (dentro de la colección) que contengan el siguiente formato de atributos: ");
                        waitTime(waitMillis);
                        for (int j = 0; j < attributesList.length; j++) {
                            System.out.printf("%-10s " + attributesType[j] + "(" + attributesLength[j] + ")\n",
                                    attributesList[j]);
                            waitTime(1000);
                        }
                        System.out.println("");
                        break;
                    case "rocksdb":
                        // Mensaje informativo sobre atributos.
                        System.out.println(
                                "Debido a la naturaleza del motor de BD, no se realizará la creación de una tabla.");
                        waitTime(waitMillis);
                        System.out.println(
                                "Para realizar una comparativa correcta, se realizará la inserción de pares clave-valor respetando el siguiente formato de atributos: ");
                        waitTime(waitMillis);
                        for (int j = 0; j < attributesList.length; j++) {
                            System.out.printf("%-10s " + attributesType[j] + "(" + attributesLength[j] + ")\n",
                                    attributesList[j]);
                            waitTime(1000);
                        }
                        System.out.println("");
                        break;
                    default:
                        break;
                    }

                    waitTime(2000);

                    // Creación de la tabla 'TestTable'
                    // En la creación de una nueva tabla se crea automáticamente el atributo "id"
                    // como PK. Input de nombre de tabla deshabilitado.
                    element.createNewTable(dbName, tableName, attributesList, attributesType, attributesLength);

                    // Mensaje informativo.
                    // Pedido de ingreso de cantidad de registros a generar.
                    System.out.println("Generación y alta de registros en la BD.");
                    waitTime(waitMillis);

                    switch (element.getEngineName().toLowerCase()) {
                    case "sqlite":
                        System.out.println(
                                "Se ingresarán " + cantidadAInsertar + " registros en la tabla " + tableName + ".\n");
                        waitTime(waitMillis);
                        break;
                    case "rocksdb":
                    case "mongo":
                    case "objectBox":
                    case "db4o":
                        System.out.println("Se ingresarán " + cantidadAInsertar + " registros en la BD.\n");
                        waitTime(waitMillis);
                        break;

                    default:
                        break;
                    }

                    // Operación CREATE (Alta de registros)
                    element.insertData(dbName, tableName, cantidadAInsertar);

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

                    element.readData(dbName, tableName);

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

                    element.updateData(dbName, tableName, attributesList, attributesType, attributesLength);
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

                    element.deleteData(dbName, tableName);
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
                menuOption = 0;
                break;
            case 2:
                // Test con carga de atributos manual
                // Comando para ver estructura de tabla en SQLite:
                // PRAGMA table_info(testtable);
                // Devuelve una tabla:
                // El atributo 'name' contiene los nombres de los campos.
                // El atributo 'type' contiene el tipo de dato que guarda ese campo.
                // El atributo 'notnull' indica (con 1 o 0, 1 para afirmativo) si el campo es
                // NOT NULL.
                // El atributo 'pk' indica (con 1 o 0, 1 para afirmativo) si el campo es PRIMARY
                // KEY.
                /*
                 * System.out.println("INGRESO DE ATRIBUTOS");
                 * 
                 * do {
                 * System.out.print("Indique la cantidad de atributos que desea ingresar: ");
                 * attributeQty = input.nextInt(); System.out.println(""); if (attributeQty < 1)
                 * { System.out.print("Cantidad inválida."); } } while (attributeQty < 1);
                 * 
                 * for (int i = 0; i < attributeQty; i++) {
                 * 
                 * System.out.println("Atributo " + (i + 1));
                 * System.out.print("Ingrese el nombre del atributo nuevo: "); attributeName =
                 * input.next();
                 * 
                 * do { System.out.
                 * print("Ingrese el tipo de dato que almacenará el atributo (VARCHAR, INT, DOUBLE): "
                 * ); attributeType = input.next(); System.out.println("");
                 * 
                 * if (!(attributeType.equalsIgnoreCase("int") ||
                 * attributeType.equalsIgnoreCase("double") ||
                 * attributeType.equalsIgnoreCase("varchar"))) {
                 * System.out.println("Ingreso incorrecto."); } } while
                 * (!(attributeType.equalsIgnoreCase("int") ||
                 * attributeType.equalsIgnoreCase("double") ||
                 * attributeType.equalsIgnoreCase("varchar")));
                 * 
                 * if (attributeType.equalsIgnoreCase("varchar")) { do {
                 * System.out.print("Ingrese el tamaño de la variable (1-255): "); attributeSize
                 * = input.nextInt(); System.out.println("");
                 * 
                 * if (attributeSize < 1 || attributeSize > 255) {
                 * System.out.println("Tamaño incorrecto."); } } while (attributeSize < 1 ||
                 * attributeSize > 255); }
                 * 
                 * sqliteCreator.createNewAttribute(dbName, tableName, attributeName,
                 * attributeType, attributeSize);
                 * 
                 * // Presione ENTER para continuar
                 * System.out.print("Presione ENTER para continuar..."); try { System.in.read();
                 * } catch (Exception e) { e.printStackTrace(); } System.out.println("");
                 */

                // FALTA VALIDAR EL TAMAÑO DEL TEXTO INGRESADO.
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!.
                // if (attributeType.equalsIgnoreCase("varchar")) {
                // System.out.print("Ingrese el texto para el atributo: ");
                // attributeText = input.next();
                // System.out.println("");
                // creator.insertText(dbName, tableName, attributeName, attributeText);
                // } else {
                // FALTA VALIDAR EL VALOR INGRESADO.
                // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!.
                // System.out.print("Ingrese un valor para el atributo: ");
                // attributeValue = input.nextInt();
                // System.out.println("");
                // creator.insertNumber(dbName, tableName, attributeName, attributeValue);
                // }

                // Presione ENTER para continuar
                // System.out.print("Presione ENTER para continuar...");
                // try {
                // System.in.read();
                // } catch (Exception e) {
                // e.printStackTrace();
                // }
                // System.out.println("");
                // }
                break;

            } // Cierro switch
        } while (menuOption != 0);

        // Tabla para mostrar resultados
        // CRUD: Create, Read, Update, Delete !!!!!!!!!!!!!!!!
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

        Iterator<MotorBD> itr = ll.iterator();
        while (itr.hasNext()) {
            MotorBD element = itr.next();
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
