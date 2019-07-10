package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

import mil.ea.cideso.satac.Amenaza;

@Entity
public class ObjectBoxAmenazaWrapper {
    @Id
    private long id;
    @NameInDb("AMENAZA")
    private Amenaza amenaza;
    @NameInDb("VISIBLE")
    private boolean visible;
    @NameInDb("LEIDO")
    private boolean leido;

    public ObjectBoxAmenazaWrapper(long id, Amenaza amenaza, boolean visible, boolean leido) {
        this.id = id;
        this.amenaza = amenaza;
        this.visible = visible;
        this.leido = leido;
    }

    public ObjectBoxAmenazaWrapper() {

    }

    public Amenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(Amenaza amenaza) {
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

}