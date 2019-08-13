package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxTiempo {
    @Id
    private long id;
    private int epoch;
    public long amenazaId;
    public ToOne<ObjectBoxAmenaza> amenaza;

    @Transient
    BoxStore __boxStore;

    public ObjectBoxTiempo() {
        this.amenaza = new ToOne<>(this, ObjectBoxTiempo_.amenaza);
    }

    public ObjectBoxTiempo(long id, int epoch) {
        this.id = id;
        this.epoch = epoch;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
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