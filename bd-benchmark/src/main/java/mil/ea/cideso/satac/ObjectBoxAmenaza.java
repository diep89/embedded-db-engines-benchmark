package mil.ea.cideso.satac;

// import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
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
	public ToOne<ObjectBoxAmenazaWrapper> amenazaWrapper;

	public ObjectBoxAmenaza(long id, int codigoSimbolo, int radioAccion, int identificacion, int tamanios) {
		this.id = id;
		this.codigoSimbolo = codigoSimbolo;
		this.radioAccion = radioAccion;
		this.identificacion = identificacion;
		this.tamanios = tamanios;
	}

	public ObjectBoxAmenaza() {
		this.amenazaWrapper = new ToOne<>(this, ObjectBoxAmenaza_.amenazaWrapper);
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

	public ToOne<ObjectBoxAmenazaWrapper> getAmenazaWrapper() {
		return amenazaWrapper;
	}

	public void setAmenazaWrapper(ToOne<ObjectBoxAmenazaWrapper> amenazaWrapper) {
		this.amenazaWrapper = amenazaWrapper;
	}

}