package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxEquipamiento {
    @Id
    private long id;
    private int cantidad;
    private int equipo;
    private int tipo;
    public long amenazaId;
    public ToOne<ObjectBoxAmenaza> amenaza;

    @Transient
    BoxStore __boxStore;

    public ObjectBoxEquipamiento() {
        this.amenaza = new ToOne<>(this, ObjectBoxEquipamiento_.amenaza);
    }

    public ObjectBoxEquipamiento(long id, int cantidad, int equipo, int tipo) {
        this.id = id;
        this.cantidad = cantidad;
        this.equipo = equipo;
        this.tipo = tipo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getEquipo() {
        return equipo;
    }

    public void setEquipo(int equipo) {
        this.equipo = equipo;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
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