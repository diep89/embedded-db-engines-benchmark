package mil.ea.cideso.satac;

public class Db4oPosicion {

    private double latitud;
    private double longitud;
    private int milisegundosFechaHora;

    public Db4oPosicion(double latitud, double longitud, int milisegundosFechaHora) {
        this.latitud = latitud;
        this.longitud = longitud;
        this.milisegundosFechaHora = milisegundosFechaHora;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public int getMilisegundosFechaHora() {
        return milisegundosFechaHora;
    }

    public void setMilisegundosFechaHora(int milisegundosFechaHora) {
        this.milisegundosFechaHora = milisegundosFechaHora;
    }

}