package mil.ea.cideso.satac;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.objectbox.Box;
import io.objectbox.BoxStore;

public class ObjectBoxCreator extends MotorBD {
	private BoxStore store = null;
	private Box<ObjectBoxTiempo> tiempoBox = null;
	private Box<ObjectBoxPosicion> posicionBox = null;
	private Box<ObjectBoxEquipamiento> equipamientoBox = null;
	private Box<ObjectBoxInformante> informanteBox = null;
	private Box<ObjectBoxAmenaza> amenazaBox = null;
	private Box<ObjectBoxAmenazaWrapper> amenazaWrapperBox = null;

	private List<ObjectBoxAmenazaWrapper> amenazaWrapperList = new ArrayList<>();
	private List<ObjectBoxAmenaza> amenazaList = new ArrayList<>();
	private List<ObjectBoxTiempo> tiempoList = new ArrayList<>();
	private List<ObjectBoxPosicion> posicionList = new ArrayList<>();
	private List<ObjectBoxInformante> informanteList = new ArrayList<>();
	private List<ObjectBoxEquipamiento> equipamientoList = new ArrayList<>();

	private int cantidadAInsertar;

	public ObjectBoxCreator() {
		setEngineName("ObjectBox");
		setEngineVersion("2.3.4");
	}

	private void createMyObjectBox(String dbName) throws IOException {
		if (getStore() == null) {
			setStore(MyObjectBox.builder().name(dbName).build());
		}
	}

	@Override
	public void createNewDatabase(String dbName) {
		setDbName(dbName);
		try {
			getTimer().start();
			createMyObjectBox(dbName);
			getTimer().stop();
			setStatsCreateOperation(getTimer().toString()); // Guardo las estadísticas de la operación.
			setTimer(getTimer().reset()); // Reseteo el timer.
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	// Mensaje:
	// [WARN ] Skipped low-level close of cursor (write, TX #xxx alive)
	// Issue en progreso:
	// https://github.com/objectbox/objectbox-java/issues/196
	public void insertData(int cantidadAInsertar) {
		setCantidadAInsertar(cantidadAInsertar);

		getTimer().start();

		initializeBoxes();

		for (int i = 0; i < cantidadAInsertar; i++) {
			generarAmenazaWrapperOB();
		}

		getStore().runInTx(() -> {
			for (ObjectBoxAmenazaWrapper amenazaWrapper : getAmenazaWrapperList())
				getAmenazaWrapperBox().put(amenazaWrapper);
			for (ObjectBoxAmenaza amenaza : getAmenazaList())
				getAmenazaBox().put(amenaza);
			for (ObjectBoxTiempo tiempo : getTiempoList())
				getTiempoBox().put(tiempo);
			for (ObjectBoxPosicion posicion : getPosicionList())
				getPosicionBox().put(posicion);
			for (ObjectBoxEquipamiento equipamiento : getEquipamientoList())
				getEquipamientoBox().put(equipamiento);
			for (ObjectBoxInformante informante : getInformanteList())
				getInformanteBox().put(informante);
		});

		getTimer().stop();

		System.out.println("");
		System.out.println("Registros insertados correctamente.");

		System.out.println("");
		System.out.println("");

		setStatsInsertOperation(getTimer().toString()); // Guardo el timer para operación CREATE.
		setTimer(getTimer().reset()); // Reseteo el timer.
		clearLists();

	}

	@Override
	public void readData() {
		getTimer().start();
		// Se realiza un query al box de objetos AmenazaWrapper con un filtro
		// que devuelva todos los elementos. En este caso, el filtro seteado es
		// el campo leído en 'false'.

		List<ObjectBoxAmenazaWrapper> amenazaWrapperList = getAmenazaWrapperBox().query()
				.equal(ObjectBoxAmenazaWrapper_.leido, false).build().find();

		getTimer().stop();
		int cantidadDeAmenazas = amenazaWrapperList.size();

		System.out.println("Registros leídos correctamente.");
		System.out.println("Se encontraron " + cantidadDeAmenazas + " amenazas guardadas en la BD.\n");

		// Comprobación. Comentar durante tests.
		// comprobarDatos();

		System.out.println("");
		System.out.println("");

		setStatsReadOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void updateData() {
		getTimer().start();

		List<ObjectBoxAmenazaWrapper> amenazaWrapperList = getAmenazaWrapperBox().query()
				.equal(ObjectBoxAmenazaWrapper_.leido, false).build().find();

		for (int i = 0; i < amenazaWrapperList.size(); i++) {
			updateAmenazaWrapperOB(amenazaWrapperList.get(i));
		}

		getStore().runInTx(() -> {
			for (ObjectBoxAmenazaWrapper amenazaWrapper : getAmenazaWrapperList())
				getAmenazaWrapperBox().put(amenazaWrapper);
			for (ObjectBoxAmenaza amenaza : getAmenazaList())
				getAmenazaBox().put(amenaza);
			for (ObjectBoxTiempo tiempo : getTiempoList())
				getTiempoBox().put(tiempo);
			for (ObjectBoxPosicion posicion : getPosicionList())
				getPosicionBox().put(posicion);
			for (ObjectBoxEquipamiento equipamiento : getEquipamientoList())
				getEquipamientoBox().put(equipamiento);
			for (ObjectBoxInformante informante : getInformanteList())
				getInformanteBox().put(informante);
		});

		getTimer().stop();

		System.out.println("");
		System.out.println("Registros actualizados correctamente.");
		System.out.println("");

		// comprobarDatos();

		setStatsUpdateOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void deleteData() {
		getTimer().start();

		try {
			if (getStore() != null) {
				getStore().close();
				getStore().deleteAllFiles();
			}
			getTimer().stop();
			setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
			setTimer(getTimer().reset()); // Reseteo el timer.
		} catch (Exception e) {
			System.out.println("Error.");
			System.out.println("Detalle del error: \n" + e.getMessage());
			System.out.println("\nStacktrace:\n\n");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Registros eliminados correctamente.");
		System.out.println("");

		setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void dropDatabase() {

	}

	public void initializeBoxes() {
		setAmenazaWrapperBox(getStore().boxFor(ObjectBoxAmenazaWrapper.class));
		setAmenazaBox(getStore().boxFor(ObjectBoxAmenaza.class));
		setTiempoBox(getStore().boxFor(ObjectBoxTiempo.class));
		setPosicionBox(getStore().boxFor(ObjectBoxPosicion.class));
		setEquipamientoBox(getStore().boxFor(ObjectBoxEquipamiento.class));
		setInformanteBox(getStore().boxFor(ObjectBoxInformante.class));
	}

	public void clearLists() {
		getAmenazaWrapperList().clear();
		getAmenazaList().clear();
		getEquipamientoList().clear();
		getInformanteList().clear();
		getTiempoList().clear();
		getPosicionList().clear();
	}

	public void generarAmenazaWrapperOB() {
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

		getAmenazaWrapperList().add(amenazaWrapper);
		getAmenazaList().add(amenaza);
		getTiempoList().add(tiempo);
		getPosicionList().add(posicion);
		getEquipamientoList().add(equipamiento1);
		getEquipamientoList().add(equipamiento2);
		getEquipamientoList().add(equipamiento3);
		getInformanteList().add(informante);

	}

	public void updateAmenazaWrapperOB(ObjectBoxAmenazaWrapper amenazaWrapper) {

		amenazaWrapper.setLeido(true);

		ObjectBoxAmenaza amenaza = amenazaWrapper.getAmenaza();
		amenaza.setCodigoSimbolo(2);
		amenaza.setIdentificacion(2);
		amenaza.setRadioAccion(2);
		amenaza.setTamanios(2);

		ObjectBoxTiempo tiempo = amenaza.getTiempo();
		tiempo.setEpoch(2);

		ObjectBoxPosicion posicion = amenaza.getPosicion();
		posicion.setLatitud(2.5);
		posicion.setLongitud(2.5);
		posicion.setMilisegundosFechaHora(2);

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

		getAmenazaWrapperList().add(amenazaWrapper);
		getAmenazaList().add(amenaza);
		getTiempoList().add(tiempo);
		getPosicionList().add(posicion);

	}

	// comprobarDatos() accede a cada elemento dentro de AmenazaWrapperBox e imprime
	// en pantalla los valores de algunos de sus atributos. Luego accede a todos los
	// elementos ligados al objeto AmenazaWrapperBox para imprimir algunos de sus
	// atributos también.
	public void comprobarDatos() {
		// Comprobacion
		System.out.println("");
		for (ObjectBoxAmenazaWrapper amenazaWrapper : getAmenazaWrapperBox().getAll()) {
			System.out.println("AmenazaWrapper Id: " + amenazaWrapper.id);
			System.out.printf("AmenazaWrapper Leido: %s\n", amenazaWrapper.isLeido());

			ObjectBoxAmenaza amenaza = amenazaWrapper.getAmenaza();
			System.out.println("  Amenaza Id: " + amenaza.getId());
			System.out.printf("  Amenaza CodigoSimbolo: %d\n", amenaza.getCodigoSimbolo());
			System.out.printf("  Amenaza Equipamiento:\n");
			for (ObjectBoxEquipamiento equip : amenaza.getEquipamientoList()) {
				System.out.println("    Equipamiento Id: " + equip.getId());
				System.out.printf("    Equipamiento Cantidad: %d\n", equip.getCantidad());
				System.out.println("");
			}
			ObjectBoxInformante informante = amenaza.getInformante();
			System.out.println("  Informante Id: " + informante.getId());
			ObjectBoxPosicion posicion = amenaza.getPosicion();
			System.out.println("  Posicion Id: " + posicion.getId());
			System.out.printf("  Posicion Latitud: %.2f\n", posicion.getLatitud());
			ObjectBoxTiempo tiempo = amenaza.getTiempo();
			System.out.println("  Tiempo Id: " + tiempo.getId());
			System.out.printf("  Tiempo Epoch: %d\n", tiempo.getEpoch());

			System.out.println("");
			System.out.println("");
		}

		System.out.println("");
		// END Comprobacion
	}

	public Box<ObjectBoxTiempo> getTiempoBox() {
		return tiempoBox;
	}

	public void setTiempoBox(Box<ObjectBoxTiempo> tiempoBox) {
		this.tiempoBox = tiempoBox;
	}

	public Box<ObjectBoxPosicion> getPosicionBox() {
		return posicionBox;
	}

	public void setPosicionBox(Box<ObjectBoxPosicion> posicionBox) {
		this.posicionBox = posicionBox;
	}

	public Box<ObjectBoxEquipamiento> getEquipamientoBox() {
		return equipamientoBox;
	}

	public void setEquipamientoBox(Box<ObjectBoxEquipamiento> equipamientoBox) {
		this.equipamientoBox = equipamientoBox;
	}

	public Box<ObjectBoxInformante> getInformanteBox() {
		return informanteBox;
	}

	public void setInformanteBox(Box<ObjectBoxInformante> informanteBox) {
		this.informanteBox = informanteBox;
	}

	public Box<ObjectBoxAmenaza> getAmenazaBox() {
		return amenazaBox;
	}

	public void setAmenazaBox(Box<ObjectBoxAmenaza> amenazaBox) {
		this.amenazaBox = amenazaBox;
	}

	public Box<ObjectBoxAmenazaWrapper> getAmenazaWrapperBox() {
		return amenazaWrapperBox;
	}

	public void setAmenazaWrapperBox(Box<ObjectBoxAmenazaWrapper> amenazaWrapperBox) {
		this.amenazaWrapperBox = amenazaWrapperBox;
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
