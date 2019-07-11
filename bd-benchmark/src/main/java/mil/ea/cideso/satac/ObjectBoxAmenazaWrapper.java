package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

import mil.ea.cideso.satac.ObjectBoxAmenaza;

@Entity
public class ObjectBoxAmenazaWrapper {
    @Id
    private long id;
    private ObjectBoxAmenaza amenaza;
    @NameInDb("VISIBLE")
    private boolean visible;
    @NameInDb("LEIDO")
    private boolean leido;

    public ObjectBoxAmenazaWrapper(long id, ObjectBoxAmenaza amenaza, boolean visible, boolean leido) {
        this.id = id;
        this.amenaza = amenaza;
        this.visible = visible;
        this.leido = leido;
    }

    public ObjectBoxAmenazaWrapper() {

    }

    public ObjectBoxAmenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(ObjectBoxAmenaza amenaza) {
        this.amenaza = amenaza;
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

}