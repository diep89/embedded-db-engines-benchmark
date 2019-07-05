package mil.ea.cideso.satac;

public class Db4oEquipamiento {
    private int cantidad;
    private int equipo;
    private int tipo;

    public Db4oEquipamiento(int cantidad, int equipo, int tipo) {
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
}