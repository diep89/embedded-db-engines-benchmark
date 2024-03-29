package mil.ea.cideso.satac;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		@SuppressWarnings("unused")
		org.jboss.logging.Logger logger = org.jboss.logging.Logger.getLogger("org.hibernate");
		java.util.logging.Logger.getLogger("org.hibernate").setLevel(java.util.logging.Level.SEVERE);

		// Nombre de la BD a generar.
		// Se inicializa automáticamente dentro del loop de pruebas.
		String dbName;

		// Variables auxiliares.
		Scanner input = new Scanner(System.in);
		int opcionMotor;
		int cantidadAInsertar;
		// String eliminar;
		int waitMillis = 1500;

		System.out.println("Practica Profesional Supervisada");
		waitTime(waitMillis);
		System.out.println("Benchmark de motores de BD embebidos");
		waitTime(waitMillis);
		System.out.println("");
		System.out.println("Test automatizado");
		waitTime(waitMillis);
		System.out.println("");
		System.out.println("Motores de BD:");
		waitTime(waitMillis);
		System.out.println("1. SQLite v3.28.0");
		System.out.println("2. DB4O v7.7.67");
		System.out.println("3. Object Box v2.3.4");
		System.out.println("4. RocksDB v6.0.1");
		System.out.println("5. H2 v1.4.199");
		System.out.println("");
		waitTime(waitMillis);
		System.out.printf("Seleccione un motor de BD: ");
		opcionMotor = input.nextInt();
		System.out.println("");
		do {
			if (opcionMotor < 1 || opcionMotor > 5) {
				System.out.print("Error. Por favor, elija una opción válida: ");
				opcionMotor = input.nextInt();
				System.out.println("");
			}
		} while (opcionMotor < 1 || opcionMotor > 5);

		List<MotorBD> ll = new LinkedList<>();
		// Instanciación de la clase creadora de la BD.
		switch (opcionMotor) {
		case 1:
			SqliteCreator sqlite = new SqliteCreator();
			ll.add(sqlite);
			break;
		case 2:
			Db4oCreator db4o = new Db4oCreator();
			ll.add(db4o);
			break;
		case 3:
			ObjectBoxCreator objectBox = new ObjectBoxCreator();
			ll.add(objectBox);
			break;
		case 4:
			RocksDbCreator rocks = new RocksDbCreator();
			ll.add(rocks);
			break;
		case 5:
			H2Creator h2 = new H2Creator();
			ll.add(h2);
			break;
		default:
			break;
		}

		// Test automatizado
		System.out.println("***\n");
		System.out.println("Inicio de test automatizado\n");
		waitTime(waitMillis);
		System.out.println(
				"Se realizarán 5 operaciones por motor:\n1) CREATE: Creación de la BD.\n2) INSERT: Alta de registros.\n3) READ: Lectura de los regitros.\n4) UPDATE: Modificación de registros.\n5) DELETE: Baja de registros.\n");
		waitTime(waitMillis);
		System.out.print("Indique la cantidad de registros a ingresar durante las operaciones de creación (1-10000): ");
		cantidadAInsertar = input.nextInt();
		System.out.println("");
		do {
			if (cantidadAInsertar < 1 || cantidadAInsertar > 10000) {
				System.out.print("Error: cantidad inválida. Por favor, elija una cantidad válida: ");
				cantidadAInsertar = input.nextInt();
				System.out.println("");
			}
		} while (cantidadAInsertar < 1 || cantidadAInsertar > 10000);

		// System.out.println("");
		waitTime(waitMillis);

		// Creación de objeto 'iterator' para recorrer todas las instancias contenidas
		// en la lista.
		Iterator<MotorBD> itr = ll.iterator();

		while (itr.hasNext()) {
			MotorBD element = itr.next();
			System.out.printf("**********\n\n");
			System.out.printf("Motor: %s v%s\n", element.getEngineName(), element.getEngineVersion());
			if (element.getProviderName() != null) {
				System.out.printf("\nProvider: %s v%s\n", element.getProviderName(), element.getProviderVersion());
				System.out.println("Mensajes LOG: SEVERE.");
				waitTime(waitMillis);
			}
			System.out.println("");
			waitTime(waitMillis);

			System.out.println("1. Operación CREATE:\n");
			waitTime(waitMillis);
			dbName = "testDb" + "-" + element.getEngineName();
			element.createNewDatabase(dbName);
			System.out.println("Motor: " + element.getEngineName() + "\nOperación 'CREATE' finalizada.\n");
			waitTime(waitMillis);
			System.out.println("");

			System.out.println("2. Operación INSERT:\n");
			waitTime(waitMillis);
			System.out.println("Generación y alta de registros en la BD.");
			waitTime(waitMillis);

			System.out.println("Se ingresarán " + cantidadAInsertar + " registros en la BD.\n");
			waitTime(waitMillis);

			// Operación INSERT (Alta de registros)
			element.insertData(cantidadAInsertar);

			// Mensaje informativo
			System.out.println("Motor: " + element.getEngineName() + "\nOperación 'INSERT' finalizada.\n");
			waitTime(waitMillis);
			System.out.println("");

			// OPERACIÓN READ
			System.out.println("3. Operación READ:\n");
			waitTime(waitMillis);
			System.out.println("Se leerán los registros ingresados en la BD.\n");
			waitTime(waitMillis);
			System.out.println("");

			element.readData();

			// Mensaje informativo
			System.out.println("Motor: " + element.getEngineName() + "\nOperación 'READ' finalizada.\n");
			waitTime(waitMillis);
			System.out.println("");

			// pressEnter();

			// OPERACIÓN UPDATE
			System.out.println("4. Operación UPDATE:\n");
			waitTime(waitMillis);
			System.out.println("Se actualizarán los registros ingresados en la BD con nuevos datos.\n");
			waitTime(waitMillis);
			System.out.println("");

			element.updateData();
			System.out.println("Motor: " + element.getEngineName() + "\nOperación 'UPDATE' finalizada.\n");
			waitTime(waitMillis);
			System.out.println("");

			pressEnter();

			// OPERACIÓN DELETE
			System.out.println("5. Operación DELETE:\n");
			waitTime(waitMillis);
			System.out.println("Se eliminarán los registros ingresados en la BD.\n");
			waitTime(waitMillis);
			System.out.println("");

			element.deleteData();
			System.out.println("Motor: " + element.getEngineName() + "\nOperación 'DELETE' finalizada.\n");
			waitTime(waitMillis);
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
		}

		// Tabla para mostrar resultados
		System.out.println("");
		System.out.println("***");
		System.out.println("");
		System.out.println("Resultados del benchmark");
		System.out.println("Operaciones: Create, Insert, Read, Update, Delete.");
		System.out.println("");

		waitTime(waitMillis);
		System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s", "Motor", "Create", "Insert", "Read", "Update",
				"Delete");
		System.out.println("");
		System.out.println("");

		Iterator<MotorBD> itrResults = ll.iterator();
		while (itrResults.hasNext()) {
			MotorBD element = itrResults.next();
			System.out.printf("%-15s %-15s %-15s %-15s %-15s %-15s\n", element.getEngineName(),
					element.getStatsCreateOperation(), element.getStatsInsertOperation(),
					element.getStatsReadOperation(), element.getStatsUpdateOperation(),
					element.getStatsDeleteOperation());
		}

		System.out.println("");
		System.out.println("");
		pressEnter();

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