package mil.ea.cideso.satac;

import java.io.IOException;
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
			createMyObjectBox(dbName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insertData(int cantidadAInsertar) {
		setCantidadAInsertar(cantidadAInsertar);

		getTimer().start();

		for (int i = 0; i < cantidadAInsertar; i++) {
			// Creación de elementos a persistir.
			ObjectBoxAmenazaWrapper amenazaWrapper = new ObjectBoxAmenazaWrapper();
			amenazaWrapper.setVisible(true);
			amenazaWrapper.setLeido(false);
			ObjectBoxAmenaza amenaza = new ObjectBoxAmenaza();
			amenaza.setCodigoSimbolo(1);
			amenaza.setIdentificacion(1);
			amenaza.setRadioAccion(1);
			amenaza.setTamanios(1);
			ObjectBoxTiempo tiempo = new ObjectBoxTiempo();
			tiempo.setEpoch(1);
			ObjectBoxPosicion posicion = new ObjectBoxPosicion();
			posicion.setLatitud(1.5);
			posicion.setLongitud(1.5);
			posicion.setMilisegundosFechaHora(1);
			ObjectBoxEquipamiento equipamiento = new ObjectBoxEquipamiento();
			equipamiento.setCantidad(1);
			equipamiento.setEquipo(1);
			equipamiento.setTipo(1);
			ObjectBoxInformante informante = new ObjectBoxInformante();

			// Declaración de relaciones entre objetos:
			getAmenazaWrapperBox().put(amenazaWrapper);
			amenaza.amenazaWrapper.setTarget(amenazaWrapper);
			getAmenazaBox().put(amenaza);
			amenazaWrapper.amenaza.setTarget(amenaza);
			tiempo.amenaza.setTarget(amenaza);
			getTiempoBox().put(tiempo);
			posicion.amenaza.setTarget(amenaza);
			getPosicionBox().put(posicion);
			equipamiento.amenaza.setTarget(amenaza);
			getEquipamientoBox().put(equipamiento);
			informante.amenaza.setTarget(amenaza);
			getInformanteBox().put(informante);

			getTimer().stop();

			if (i + 1 < cantidadAInsertar) {
				System.out.print((i + 1) + " - ");
			} else {
				System.out.print((i + 1) + ".");
			}
			getTimer().start();
		}
		getTimer().stop();

		System.out.println("");
		System.out.println("");

		setStatsCreateOperation(getTimer().toString()); // Guardo el timer para operación CREATE.
		setTimer(getTimer().reset()); // Reseteo el timer.
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

		// for (ObjectBoxAmenazaWrapper amenazaWrapper :
		// getAmenazaWrapperBox().getAll()) {
		// System.out.println("AmenazaWrapper Id: " + amenazaWrapper.id);
		// }

		// System.out.println("");

		// for (ObjectBoxAmenaza amenaza : getAmenazaBox().getAll()) {
		// System.out.println("Amenaza Id: " + amenaza.id);
		// }

		System.out.println("");
		System.out.println("");

		setStatsReadOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void updateData() {
		getTimer().start();

		// Actualizo los objetos AmenazaWrapper
		for (ObjectBoxAmenazaWrapper amenazaWrapper : getAmenazaWrapperBox().getAll()) {
			amenazaWrapper.setLeido(true);
			getAmenazaWrapperBox().put(amenazaWrapper);
		}

		// Actualizo los objetos Amenaza
		for (ObjectBoxAmenaza amenaza : getAmenazaBox().getAll()) {
			amenaza.setCodigoSimbolo(2);
			amenaza.setRadioAccion(2);
			amenaza.setIdentificacion(2);
			amenaza.setTamanios(2);
			getAmenazaBox().put(amenaza);
		}

		// Actualizo el objeto Tiempo
		for (ObjectBoxTiempo tiempo : getTiempoBox().getAll()) {
			tiempo.setEpoch(2);
			getTiempoBox().put(tiempo);
		}

		// Actualizo el objeto Posicion
		for (ObjectBoxPosicion posicion : getPosicionBox().getAll()) {
			posicion.setLatitud(2.5);
			posicion.setLongitud(2.5);
			posicion.setMilisegundosFechaHora(2);
			getPosicionBox().put(posicion);
		}

		// Actualizo el objeto Equipamiento
		for (ObjectBoxEquipamiento equipamiento : getEquipamientoBox().getAll()) {
			equipamiento.setCantidad(2);
			equipamiento.setEquipo(2);
			equipamiento.setTipo(2);
			getEquipamientoBox().put(equipamiento);
		}

		// Dado que el objeto 'Informante' no contiene atributos modificables,
		// no se realiza modificación alguna.

		// No se modifica ninguna de las relaciones entre los objetos.

		getTimer().stop();

		System.out.println("Registros actualizados correctamente.");
		System.out.println("");

		setStatsUpdateOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void deleteData() {
		getTimer().start();
		getAmenazaWrapperBox().removeAll();
		getAmenazaBox().removeAll();
		getTiempoBox().removeAll();
		getPosicionBox().removeAll();
		getEquipamientoBox().removeAll();
		getInformanteBox().removeAll();
		store.close();
		getTimer().stop();

		System.out.println("Registros eliminados correctamente.");
		System.out.println("");

		setStatsDeleteOperation(getTimer().toString()); // Guardo el timer para operación READ.
		setTimer(getTimer().reset()); // Reseteo el timer.
	}

	@Override
	public void dropDatabase() {

	}

	public Box<ObjectBoxTiempo> getTiempoBox() {
		if (tiempoBox == null) {
			tiempoBox = getStore().boxFor(ObjectBoxTiempo.class);
		}
		return tiempoBox;
	}

	public void setTiempoBox(Box<ObjectBoxTiempo> tiempoBox) {
		this.tiempoBox = tiempoBox;
	}

	public Box<ObjectBoxPosicion> getPosicionBox() {
		if (posicionBox == null) {
			posicionBox = getStore().boxFor(ObjectBoxPosicion.class);
		}
		return posicionBox;
	}

	public void setPosicionBox(Box<ObjectBoxPosicion> posicionBox) {
		this.posicionBox = posicionBox;
	}

	public Box<ObjectBoxEquipamiento> getEquipamientoBox() {
		if (equipamientoBox == null) {
			equipamientoBox = getStore().boxFor(ObjectBoxEquipamiento.class);
		}
		return equipamientoBox;
	}

	public void setEquipamientoBox(Box<ObjectBoxEquipamiento> equipamientoBox) {
		this.equipamientoBox = equipamientoBox;
	}

	public Box<ObjectBoxInformante> getInformanteBox() {
		if (informanteBox == null) {
			informanteBox = getStore().boxFor(ObjectBoxInformante.class);
		}
		return informanteBox;
	}

	public void setInformanteBox(Box<ObjectBoxInformante> informanteBox) {
		this.informanteBox = informanteBox;
	}

	public Box<ObjectBoxAmenaza> getAmenazaBox() {
		if (amenazaBox == null) {
			amenazaBox = getStore().boxFor(ObjectBoxAmenaza.class);
		}
		return amenazaBox;
	}

	public void setAmenazaBox(Box<ObjectBoxAmenaza> amenazaBox) {
		this.amenazaBox = amenazaBox;
	}

	public Box<ObjectBoxAmenazaWrapper> getAmenazaWrapperBox() {
		if (amenazaWrapperBox == null) {
			amenazaWrapperBox = getStore().boxFor(ObjectBoxAmenazaWrapper.class);
		}
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

}
