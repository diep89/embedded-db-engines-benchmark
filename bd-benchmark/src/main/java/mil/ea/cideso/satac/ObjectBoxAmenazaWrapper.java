package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class ObjectBoxAmenazaWrapper {
    @Id
    private long id;
    @NameInDb("VISIBLE")
    private boolean visible;
    @NameInDb("LEIDO")
    private boolean leido;

    public ObjectBoxAmenazaWrapper() {
    }

    public ObjectBoxAmenazaWrapper(long id, boolean visible, boolean leido) {
        this.id = id;
        this.visible = visible;
        this.leido = leido;
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