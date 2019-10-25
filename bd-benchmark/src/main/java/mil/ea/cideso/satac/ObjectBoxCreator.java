/* En la línea 270 se encuentra el método 'comprobacion', para facilitar 
el correcto funcionamiento de las operaciones. Dentro del método existe una 
segunda alternativa de búsqueda deshabilitada que es igualmente válida.
Dentro de los métodos de prueba se encuentra deshabilitada.
*/

package mil.ea.cideso.satac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
	private BoxStore store = null;

	private List<ObjectBoxAmenazaWrapper> amenazaWrapperList = new ArrayList<>();
	
	private int cantidadAInsertar;

	public ObjectBoxCreator() {
		setEngineName("ObjectBox");
		setEngineVersion("2.3.4");
	}

	@Override
	public void createNewDatabase(String dbName) {
		setDbName(dbName);
		try {
			getTimer().start();
			createMyObjectBox(dbName);
			getTimer().stop();
		} catch (IOException e) {
			e.printStackTrace();
		}

		setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	// Sobre el mensaje en pantalla:
	// [WARN ] Skipped low-level close of cursor (write, TX #xxx alive)
	// Es una issue actualmente en progreso:
	// https://github.com/objectbox/objectbox-java/issues/196
	public void insertData(int cantidadAInsertar) {
		setCantidadAInsertar(cantidadAInsertar);

		List<ObjectBoxAmenazaWrapper> testObjectsList = generateTestObjectsOB();

		getTimer().start();
		persist(testObjectsList);
		getTimer().stop();

		clearLists();

		comprobacion();

		System.out.println("\nRegistros insertados correctamente.\n\n");

		setStatsInsertOperation(getTimer().toString()); // Guardo el timer para operación CREATE.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void readData() {
		getTimer().start();
		readTestObjectsOB();
		getTimer().stop();

		// comprobacion();

		System.out.println("\nRegistros leídos correctamente.\n\n");

		long med = getTimer().elapsed(TimeUnit.NANOSECONDS);
		setStatsReadOperation(Long.toString(med)); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void updateData() {
		getTimer().start();
		updateTestObjectsOB();
		getTimer().stop();

		// comprobacion();
		
		System.out.println("\nRegistros actualizados correctamente.\n\n");

		setStatsUpdateOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void deleteData() {
		getTimer().start();
		deleteTestObjectsOB();
		getTimer().stop();

		System.out.println("\nRegistros eliminados correctamente.\n\n");

		setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void dropDatabase() {

	}

	private void createMyObjectBox(String dbName) throws IOException {
		if (getStore() == null) {
			setStore(MyObjectBox.builder().name(dbName).build());
		}
	}

	public List<ObjectBoxAmenazaWrapper> generateTestObjectsOB() {
		List<ObjectBoxAmenazaWrapper> testObjectsList = new ArrayList<>(getCantidadAInsertar());

		for (int i = 0; i < getCantidadAInsertar(); i++) {
			ObjectBoxAmenazaWrapper amenazaWrapper = new ObjectBoxAmenazaWrapper();
			amenazaWrapper.setId(0);
			amenazaWrapper.setVisible(true);
			amenazaWrapper.setLeido(false);

			ObjectBoxAmenaza amenaza = new ObjectBoxAmenaza();
			amenaza.setId(0);
			amenaza.setCodigoSimbolo(1);
			amenaza.setIdentificacion(1);
			amenaza.setRadioAccion(1);
			amenaza.setTamanios(1);

			ObjectBoxTiempo tiempo = new ObjectBoxTiempo();
			tiempo.setId(0);
			tiempo.setEpoch(1);

			ObjectBoxPosicion posicion = new ObjectBoxPosicion();
			posicion.setId(0);
			posicion.setLatitud(1.5);
			posicion.setLongitud(1.5);
			posicion.setMilisegundosFechaHora(1);

			ObjectBoxEquipamiento equipamiento1 = new ObjectBoxEquipamiento();
			equipamiento1.setId(0);
			equipamiento1.setCantidad(1);
			equipamiento1.setEquipo(1);
			equipamiento1.setTipo(1);

			ObjectBoxEquipamiento equipamiento2 = new ObjectBoxEquipamiento();
			equipamiento2.setId(0);
			equipamiento2.setCantidad(1);
			equipamiento2.setEquipo(1);
			equipamiento2.setTipo(1);

			ObjectBoxEquipamiento equipamiento3 = new ObjectBoxEquipamiento();
			equipamiento3.setId(0);
			equipamiento3.setCantidad(1);
			equipamiento3.setEquipo(1);
			equipamiento3.setTipo(1);

			ObjectBoxInformante informante = new ObjectBoxInformante();
			informante.setId(0);

			amenazaWrapper.amenaza.setTarget(amenaza);
			amenaza.amenazaWrapper.setTarget(amenazaWrapper);
			amenaza.informante.setTarget(informante);
			amenaza.posicion.setTarget(posicion);
			amenaza.tiempo.setTarget(tiempo);
			amenaza.equipamientoList.add(equipamiento1);
			amenaza.equipamientoList.add(equipamiento2);
			amenaza.equipamientoList.add(equipamiento3);
			tiempo.amenaza.setTarget(amenaza);
			posicion.amenaza.setTarget(amenaza);
			informante.amenaza.setTarget(amenaza);

			testObjectsList.add(amenazaWrapper);
		}

		return testObjectsList;
	}

	public void persist(List<ObjectBoxAmenazaWrapper> list) {
		Iterator<ObjectBoxAmenazaWrapper> listItr = list.iterator();

		while (listItr.hasNext()) {
			ObjectBoxAmenazaWrapper testObject = listItr.next();
			getStore().boxFor(ObjectBoxAmenazaWrapper.class).put(testObject);
		}
	}

	public void readTestObjectsOB() {
		List<ObjectBoxAmenazaWrapper> amenazaWrapperList = getStore().boxFor(ObjectBoxAmenazaWrapper.class).getAll();
		List<ObjectBoxAmenaza> amenazaList = getStore().boxFor(ObjectBoxAmenaza.class).getAll();
		List<ObjectBoxInformante> informanteList = getStore().boxFor(ObjectBoxInformante.class).getAll();
		List<ObjectBoxTiempo> tiempoList = getStore().boxFor(ObjectBoxTiempo.class).getAll();
		List<ObjectBoxEquipamiento> equipamientoList = getStore().boxFor(ObjectBoxEquipamiento.class).getAll();
		List<ObjectBoxPosicion> posicionList = getStore().boxFor(ObjectBoxPosicion.class).getAll();
	}

	public void updateTestObjectsOB() {
		clearLists();

		List<ObjectBoxAmenazaWrapper> amenazaWrapperList = getStore().boxFor(ObjectBoxAmenazaWrapper.class).getAll();
		Iterator<ObjectBoxAmenazaWrapper> amenazaWrapperItr = amenazaWrapperList.iterator();

		while (amenazaWrapperItr.hasNext()) {
			ObjectBoxAmenazaWrapper amenazaWrapper = amenazaWrapperItr.next();

			amenazaWrapper.setLeido(true);
			getAmenazaWrapperList().add(amenazaWrapper);

			ObjectBoxAmenaza amenaza = amenazaWrapper.getAmenaza();
			amenaza.setCodigoSimbolo(2);
			amenaza.setIdentificacion(2);
			amenaza.setRadioAccion(2);
			amenaza.setTamanios(2);
			getAmenazaList().add(amenaza);

			ObjectBoxTiempo tiempo = amenaza.getTiempo();
			tiempo.setEpoch(2);
			getTiempoList().add(tiempo);

			ObjectBoxPosicion posicion = amenaza.getPosicion();
			posicion.setLatitud(2.5);
			posicion.setLongitud(2.5);
			posicion.setMilisegundosFechaHora(2);
			getPosicionList().add(posicion);

			List<ObjectBoxEquipamiento> equipList = amenaza.getEquipamientoList();
			Iterator<ObjectBoxEquipamiento> equipItr = equipList.iterator();
			while (equipItr.hasNext()) {
				ObjectBoxEquipamiento equip = equipItr.next();
				equip.setCantidad(2);
				equip.setEquipo(2);
				equip.setTipo(2);
				getEquipamientoList().add(equip);
			}

			// Dado que el objeto 'Informante' no contiene atributos modificables,
			// no se realiza modificación alguna.

			// No se modifica ninguna de las relaciones entre los objetos.
		}

		getStore().runInTx(() -> {
			getStore().boxFor(ObjectBoxAmenazaWrapper.class).put(getAmenazaWrapperList());
			getStore().boxFor(ObjectBoxAmenaza.class).put(getAmenazaList());
			getStore().boxFor(ObjectBoxTiempo.class).put(getTiempoList());
			getStore().boxFor(ObjectBoxPosicion.class).put(getPosicionList());
			getStore().boxFor(ObjectBoxEquipamiento.class).put(getEquipamientoList());
			getStore().boxFor(ObjectBoxInformante.class).put(getInformanteList());
		});
	}

	public void deleteTestObjectsOB() {
		try {
			if (getStore() != null) {
				getStore().close();
				getStore().deleteAllFiles();
			}
		} catch (Exception e) {
			System.out.println("Error.");
			System.out.println("Detalle del error: \n" + e.getMessage());
			System.out.println("\nStacktrace:\n\n");
			e.printStackTrace();
		}
	}

	// El método 'comprobacion()' accede a cada elemento dentro de AmenazaWrapperBox
	// e imprime
	// en pantalla los valores de algunos de sus atributos. Luego accede a todos los
	// elementos ligados al objeto AmenazaWrapperBox para imprimir algunos de sus
	// atributos también.
	public void comprobacion() {
		// Comprobacion 1
		System.out.println("\n\nCOMPROBACION\n\n");

		List<ObjectBoxAmenazaWrapper> amenazaWrapperList = getStore().boxFor(ObjectBoxAmenazaWrapper.class).query()
				.build().find();
		List<ObjectBoxAmenaza> amenazaList = getStore().boxFor(ObjectBoxAmenaza.class).query().build().find();
		List<ObjectBoxTiempo> tiempoList = getStore().boxFor(ObjectBoxTiempo.class).query().build().find();
		List<ObjectBoxEquipamiento> equipamientoList = getStore().boxFor(ObjectBoxEquipamiento.class).query().build()
				.find();
		List<ObjectBoxInformante> informanteList = getStore().boxFor(ObjectBoxInformante.class).query().build().find();
		List<ObjectBoxPosicion> posicionList = getStore().boxFor(ObjectBoxPosicion.class).query().build().find();

		Iterator<ObjectBoxAmenazaWrapper> itr1 = amenazaWrapperList.iterator();
		Iterator<ObjectBoxAmenaza> itr2 = amenazaList.iterator();
		Iterator<ObjectBoxTiempo> itr3 = tiempoList.iterator();
		Iterator<ObjectBoxEquipamiento> itr4 = equipamientoList.iterator();
		Iterator<ObjectBoxInformante> itr5 = informanteList.iterator();
		Iterator<ObjectBoxPosicion> itr6 = posicionList.iterator();

		while (itr1.hasNext()) {
			System.out.println(itr1.next());
		}
		while (itr2.hasNext()) {
			System.out.println(itr2.next());
		}
		while (itr3.hasNext()) {
			System.out.println(itr3.next());
		}
		while (itr4.hasNext()) {
			System.out.println(itr4.next());
		}
		while (itr5.hasNext()) {
			System.out.println(itr5.next());
		}
		while (itr6.hasNext()) {
			System.out.println(itr6.next());
		}

		// END Comprobacion 1

		// Comprobacion 2
		// for (ObjectBoxAmenazaWrapper amenazaWrapper :
		// getStore().boxFor(ObjectBoxAmenazaWrapper.class).getAll()) {
		// System.out.println("AmenazaWrapper Id: " + amenazaWrapper.id);
		// System.out.printf("AmenazaWrapper Leido: %s\n", amenazaWrapper.isLeido());

		// ObjectBoxAmenaza amenaza = amenazaWrapper.getAmenaza();
		// System.out.println(" Amenaza Id: " + amenaza.getId());
		// System.out.printf(" Amenaza CodigoSimbolo: %d\n",
		// amenaza.getCodigoSimbolo());
		// System.out.printf(" Amenaza Equipamiento:\n");
		// for (ObjectBoxEquipamiento equip : amenaza.getEquipamientoList()) {
		// System.out.println(" Equipamiento Id: " + equip.getId());
		// System.out.printf(" Equipamiento Cantidad: %d\n", equip.getCantidad());
		// System.out.println("");
		// }
		// ObjectBoxInformante informante = amenaza.getInformante();
		// System.out.println(" Informante Id: " + informante.getId());
		// ObjectBoxPosicion posicion = amenaza.getPosicion();
		// System.out.println(" Posicion Id: " + posicion.getId());
		// System.out.printf(" Posicion Latitud: %.2f\n", posicion.getLatitud());
		// ObjectBoxTiempo tiempo = amenaza.getTiempo();
		// System.out.println(" Tiempo Id: " + tiempo.getId());
		// System.out.printf(" Tiempo Epoch: %d\n", tiempo.getEpoch());

		// System.out.println("");
		// System.out.println("");
		// }
		// END Comprobacion 2

		System.out.println("\n\nEND COMPROBACION\n\n");

		// Ejemplo de query:
		// List<ObjectBoxAmenazaWrapper> amenazaWrapperList =
		// getAmenazaWrapperBox().query()
		// .equal(ObjectBoxAmenazaWrapper_.leido, false).build().find();

		// No se utiliza la función runInReadTx() dado que el costo de las lecturas a la
		// bd no impacta de la manera que lo hacen las escrituras
		// https://docs.objectbox.io/transactions
		// getStore().runInReadTx(() -> {
		// });
	}

	public void clearLists() {
		getAmenazaWrapperList().clear();
		getAmenazaList().clear();
		getEquipamientoList().clear();
		getInformanteList().clear();
		getTiempoList().clear();
		getPosicionList().clear();
	}

	public BoxStore getStore() {
		return store;
	}

	public void setStore(BoxStore store) {
		this.store = store;
	}

	public int getCantidadAInsertar() {
		return cantidadAInsertar;
	}

	public void setCantidadAInsertar(int cantidadAInsertar) {
		this.cantidadAInsertar = cantidadAInsertar;
	}

	public List<ObjectBoxAmenazaWrapper> getAmenazaWrapperList() {
		return amenazaWrapperList;
	}

	public void setAmenazaWrapperList(List<ObjectBoxAmenazaWrapper> amenazaWrapperList) {
		this.amenazaWrapperList = amenazaWrapperList;
	}

	public List<ObjectBoxAmenaza> getAmenazaList() {
		return amenazaList;
	}

	public void setAmenazaList(List<ObjectBoxAmenaza> amenazaList) {
		this.amenazaList = amenazaList;
	}

	public List<ObjectBoxTiempo> getTiempoList() {
		return tiempoList;
	}

	public void setTiempoList(List<ObjectBoxTiempo> tiempoList) {
		this.tiempoList = tiempoList;
	}

	public List<ObjectBoxPosicion> getPosicionList() {
		return posicionList;
	}

	public void setPosicionList(List<ObjectBoxPosicion> posicionList) {
		this.posicionList = posicionList;
	}

	public List<ObjectBoxInformante> getInformanteList() {
		return informanteList;
	}

	public void setInformanteList(List<ObjectBoxInformante> informanteList) {
		this.informanteList = informanteList;
	}

	public List<ObjectBoxEquipamiento> getEquipamientoList() {
		return equipamientoList;
	}

	public void setEquipamientoList(List<ObjectBoxEquipamiento> equipamientoList) {
		this.equipamientoList = equipamientoList;
	}

}
