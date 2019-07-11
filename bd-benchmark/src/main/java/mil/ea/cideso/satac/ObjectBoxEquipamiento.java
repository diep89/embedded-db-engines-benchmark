package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ObjectBoxEquipamiento {
    @Id
    private long id;
    private int cantidad;
    private int equipo;
    private int tipo;

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
}