package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxAmenazaWrapper {
	@Id
	public long id;
	@NameInDb("VISIBLE")
	private boolean visible;
	@NameInDb("LEIDO")
	private boolean leido;
	public long amenazaId;
	public ToOne<ObjectBoxAmenaza> amenaza;

	@Transient
	BoxStore __boxStore;

	public ObjectBoxAmenazaWrapper(long id, boolean visible, boolean leido) {
		this.id = id;
		this.visible = visible;
		this.leido = leido;
	}

	public ObjectBoxAmenazaWrapper() {
		this.amenaza = new ToOne<>(this, ObjectBoxAmenazaWrapper_.amenaza);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public boolean isLeido() {
		return leido;
	}

	public void setLeido(boolean leido) {
		this.leido = leido;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getAmenazaId() {
		return amenazaId;
	}

	public void setAmenazaId(long amenazaId) {
		this.amenazaId = amenazaId;
	}

	// public ToOne<ObjectBoxAmenaza> getAmenaza() {
	// return amenaza;
	// }

	// public void setAmenaza(ToOne<ObjectBoxAmenaza> amenaza) {
	// this.amenaza = amenaza;
	// }

	public ObjectBoxAmenaza getAmenaza() {
		return amenaza.getTarget();
	}

	public void setAmenaza(ObjectBoxAmenaza amenaza) {
		this.amenaza.setTarget(amenaza);
	}

}