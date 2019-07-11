package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ObjectBoxTiempo {
    @Id
    private long id;
    private int epoch;

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