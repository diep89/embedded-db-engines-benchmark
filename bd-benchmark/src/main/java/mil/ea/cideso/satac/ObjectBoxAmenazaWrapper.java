package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxAmenazaWrapper {
	@Id
	public long id;
	@NameInDb("VISIBLE")
	private boolean visible;
	@NameInDb("LEIDO")
	private boolean leido;
	public ToOne<ObjectBoxAmenaza> amenaza;

	public ObjectBoxAmenazaWrapper(long id, boolean visible, boolean leido) {
		this.id = id;
		this.visible = visible;
		this.leido = leido;
	}

	public ObjectBoxAmenazaWrapper() {
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

	public ToOne<ObjectBoxAmenaza> getAmenaza() {
		return amenaza;
	}

	public void setAmenaza(ToOne<ObjectBoxAmenaza> amenaza) {
		this.amenaza = amenaza;
	}

}