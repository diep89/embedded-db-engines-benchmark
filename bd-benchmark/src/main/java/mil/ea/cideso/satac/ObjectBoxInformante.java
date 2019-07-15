package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxInformante {

    @Id
    private long id;
    public ToOne<ObjectBoxAmenaza> amenaza;

    public ObjectBoxInformante(long id, long amenazaId) {
        this.id = id;
        this.amenaza.setTargetId(amenazaId);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}