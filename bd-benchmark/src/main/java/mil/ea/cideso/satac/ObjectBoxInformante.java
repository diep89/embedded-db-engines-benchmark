package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxInformante {

    @Id
    private long id;
    public ToOne<ObjectBoxAmenaza> amenaza;

    public ObjectBoxInformante() {
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

}