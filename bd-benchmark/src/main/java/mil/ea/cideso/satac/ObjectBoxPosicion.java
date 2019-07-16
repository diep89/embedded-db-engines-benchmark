package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxPosicion {
    @Id
    private long id;
    private double latitud;
    private double longitud;
    private int milisegundosFechaHora;
    public ToOne<ObjectBoxAmenaza> amenaza;

    public ObjectBoxPosicion() {
    }

    public ObjectBoxPosicion(long id, double latitud, double longitud, int milisegundosFechaHora) {
        this.id = id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}