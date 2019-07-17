package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxTiempo {
    @Id(assignable = true)
    private long id;
    private int epoch;
    ToOne<ObjectBoxAmenaza> amenaza;

    public ObjectBoxTiempo() {
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

}