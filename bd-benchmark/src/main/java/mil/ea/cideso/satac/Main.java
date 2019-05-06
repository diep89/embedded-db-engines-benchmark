package mil.ea.cideso.satac;

import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int menuOption;
        String motores[] = { "SQLite v3.27.2.1", "MongoDB v3.11.0-beta2" };
        String stats[];
        String tableName = "TestTable";

        // Atributos definidos para el test automatizado.
        String attributesList[] = { "Edad", "Sexo", "Telefono" };
        String attributesType[] = { "int", "varchar", "int" };
        String attributesLength[] = { "2", "1", "8" };

        int cantidadAInsertar = 100;
        String timer = "";
        String eliminar;

        // int attributeQty;
        // String attributeName;
        // String attributeType;
        // Integer attributeSize = null;
        // Integer attributeValue = null;
        // String attributeText = null;
        Scanner input = new Scanner(System.in);

        System.out.println("Practica Profesional Supervisada");
        System.out.println("Benchmark de motores de BD embebidos");
        System.out.println("");
        System.out.println("Lista de motores de BD en el test:");
        for (int i = 0; i < motores.length; i++) {
            System.out.println("- " + motores[i]);
        }
        System.out.println("");

        do {
            System.out.println("Opciones:");
            System.out.println("1 - Test automatizado");
            System.out.println("2 - Test con ingreso manual de atributos");
            // System.out.println("");
            // System.out.println("");
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

            System.out.println("");

            switch (menuOption) {
            case 1:
                // Test automatizado
                SqliteCreator sqliteCreator = new SqliteCreator();
                String dbName = "testDb";
                // Creación de la BD Sqlite.
                sqliteCreator.createNewDatabase(dbName);

                // Mensaje informativo sobre atributos.
                System.out.println("Se procede a realizar la creación de la tabla de prueba en la BD.");
                System.out.println("Los atributos que se crearán son: ");
                for (int i = 0; i < attributesList.length; i++) {
                    System.out.printf("%-10s " + attributesType[i] + "(" + attributesLength[i] + ")\n",
                            attributesList[i]);
                }
                System.out.println("");

                // Presione ENTER para continuar
                System.out.print("Presione ENTER para continuar...");
                try {
                    System.in.read();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("");

                // Creación de la tabla 'TestTable'
                // En la creación de una nueva tabla se crea automáticamente el atributo "id"
                // como PK. Input de nombre de tabla deshabilitado.
                sqliteCreator.createNewTable(dbName, tableName, attributesList, attributesType, attributesLength);

                // Mensaje informativo.
                // Pedido de ingreso de cantidad de registros a generar.
                System.out.println("Generación y alta de registros en la BD.");
                System.out.print("Indique la cantidad de registros a ingresar (1-100k): ");
                cantidadAInsertar = input.nextInt();
                System.out.println("");
                do {
                    if (cantidadAInsertar < 1 || cantidadAInsertar > 100000) {
                        System.out.print("Error: cantidad inválida. Por favor, elija una cantidad válida: ");
                        cantidadAInsertar = input.nextInt();
                        System.out.println("");
                    }
                } while (cantidadAInsertar < 1 || cantidadAInsertar > 100000);

                System.out.println("Se ingresarán " + cantidadAInsertar + " registros.\n");

                // Alta de registros
                // SQLite
                sqliteCreator.insertData(dbName, tableName, cantidadAInsertar);

                // MongoDB
                // mongoDbCreator.

                // Prueba
                timer = SqliteCreator.getTimer();
                System.out.println("La operación tardó " + timer);
                System.out.println("");

                // Eliminación de la BD.
                System.out.println("Operación finalizada.");

                do {
                    System.out.print("¿Desea eliminar la BD (Y/N)? ");
                    eliminar = input.next();
                    if (!(eliminar.equalsIgnoreCase("y") || eliminar.equalsIgnoreCase("n"))) {
                        System.out.println("Respuesta inválida. Intente nuevamente.");
                    }
                } while (!(eliminar.equalsIgnoreCase("y") || eliminar.equalsIgnoreCase("n")));

                System.out.println("");

                if (eliminar.equalsIgnoreCase("y")) {
                    File file = new File(dbName);
                    sqliteCreator.dropDatabase(dbName);
                    if (file.delete()) {
                        System.out.println("El archivo '" + dbName + "' ha sido eliminado.");
                    }
                }

                System.out.println("");
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

        // Presione ENTER para continuar
        // System.out.print("Presione ENTER para continuar...");
        // try {
        // System.in.read();
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // System.out.println("");

        // Tabla para mostrar resultados
        // CRUD: Create, Read, Update, Delete !!!!!!!!!!!!!!!!
        System.out.println("Resultados del benchmark");
        System.out.println("");
        System.out.printf("%-25s %-20s", "Motor", "Create");
        System.out.println("");
        System.out.println("");
        for (

                int i = 0; i < motores.length; i++) {
            System.out.printf("%-25s %-20s", motores[i], timer);
        }

        System.out.println("");
        System.out.println("");

        input.close();
    }
}
