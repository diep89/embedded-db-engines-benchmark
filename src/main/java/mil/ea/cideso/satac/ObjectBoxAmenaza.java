package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Backlink;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToMany;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxAmenaza {
	@Id
	public long id;
	@NameInDb("COD_SIMBOLO")
	private int codigoSimbolo;
	@NameInDb("RADIO_ACCION")
	private int radioAccion;
	@NameInDb("IDENTIFICACION")
	private int identificacion;
	@NameInDb("TAMANIOS")
	private int tamanios;
	public long amenazaWrapperId;
	public long informanteId;
	public long posicionId;
	public long tiempoId;

	// Atributos para relaciones
	public ToOne<ObjectBoxAmenazaWrapper> amenazaWrapper;
	public ToOne<ObjectBoxInformante> informante;
	public ToOne<ObjectBoxPosicion> posicion;
	public ToOne<ObjectBoxTiempo> tiempo;
	@Backlink
	public ToMany<ObjectBoxEquipamiento> equipamientoList;

	@Transient
	BoxStore __boxStore;

	public ObjectBoxAmenaza(long id, int codigoSimbolo, int radioAccion, int identificacion, int tamanios) {
		this.id = id;
		this.codigoSimbolo = codigoSimbolo;
		this.radioAccion = radioAccion;
		this.identificacion = identificacion;
		this.tamanios = tamanios;
	}

	public ObjectBoxAmenaza() {
		this.amenazaWrapper = new ToOne<>(this, ObjectBoxAmenaza_.amenazaWrapper);
		this.informante = new ToOne<>(this, ObjectBoxAmenaza_.informante);
		this.posicion = new ToOne<>(this, ObjectBoxAmenaza_.posicion);
		this.tiempo = new ToOne<>(this, ObjectBoxAmenaza_.tiempo);
		this.equipamientoList = new ToMany<>(this, ObjectBoxAmenaza_.equipamientoList);
	}

	public int getCodigoSimbolo() {
		return codigoSimbolo;
	}

	public void setCodigoSimbolo(int codigoSimbolo) {
		this.codigoSimbolo = codigoSimbolo;
	}

	public int getRadioAccion() {
		return radioAccion;
	}

	public void setRadioAccion(int radioAccion) {
		this.radioAccion = radioAccion;
	}

	public int getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(int identificacion) {
		this.identificacion = identificacion;
	}

	public int getTamanios() {
		return tamanios;
	}

	public void setTamanios(int tamanios) {
		this.tamanios = tamanios;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmenazaWrapperId() {
		return amenazaWrapperId;
	}

	public void setAmenazaWrapperId(long amenazaWrapperId) {
		this.amenazaWrapperId = amenazaWrapperId;
	}

	public ToMany<ObjectBoxEquipamiento> getEquipamientoList() {
		return equipamientoList;
	}

	public ObjectBoxAmenazaWrapper getAmenazaWrapper() {
		return amenazaWrapper.getTarget();
	}

	public void setAmenazaWrapper(ObjectBoxAmenazaWrapper amenazaWrapper) {
		this.amenazaWrapper.setTarget(amenazaWrapper);
	}

	public ObjectBoxInformante getInformante() {
		return informante.getTarget();
	}

	public void setAmenazaWrapper(ObjectBoxInformante informante) {
		this.informante.setTarget(informante);
	}

	public ObjectBoxPosicion getPosicion() {
		return posicion.getTarget();
	}

	public void setPosicion(ObjectBoxPosicion posicion) {
		this.posicion.setTarget(posicion);
	}

	public ObjectBoxTiempo getTiempo() {
		return tiempo.getTarget();
	}

	public void setTiempo(ObjectBoxTiempo tiempo) {
		this.tiempo.setTarget(tiempo);
	}

}