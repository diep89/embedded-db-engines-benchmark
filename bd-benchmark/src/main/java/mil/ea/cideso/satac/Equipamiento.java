package mil.ea.cideso.satac;

public class Equipamiento {
    private int cantidad;
    private int equipo;
    private int tipo;

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
}