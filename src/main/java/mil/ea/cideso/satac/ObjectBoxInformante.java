package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxInformante {
    @Id
    private long id;
    public long amenazaId;
    public ToOne<ObjectBoxAmenaza> amenaza;

    @Transient
    BoxStore __boxStore;

    public ObjectBoxInformante() {
        this.amenaza = new ToOne<>(this, ObjectBoxInformante_.amenaza);
    }

    public ObjectBoxInformante(long id) {
        this.id = id;
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

    public ObjectBoxAmenaza getAmenaza() {
        return amenaza.getTarget();
    }

    public void setAmenaza(ObjectBoxAmenaza amenaza) {
        this.amenaza.setTarget(amenaza);
    }

}