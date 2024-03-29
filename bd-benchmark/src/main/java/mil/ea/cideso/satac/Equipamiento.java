package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Equipamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int cantidad;
    private int equipo;
    private int tipo;

    public Equipamiento() {

    }

    public Equipamiento(int cantidad, int equipo, int tipo) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}