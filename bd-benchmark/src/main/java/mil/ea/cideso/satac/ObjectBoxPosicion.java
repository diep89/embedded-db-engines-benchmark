package mil.ea.cideso.satac;

import io.objectbox.BoxStore;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.Transient;
import io.objectbox.relation.ToOne;

@Entity
public class ObjectBoxPosicion {
    @Id
    private long id;
    private double latitud;
    private double longitud;
    private int milisegundosFechaHora;
    public long amenazaId;
    public ToOne<ObjectBoxAmenaza> amenaza;

    @Transient
    BoxStore __boxStore;

    public ObjectBoxPosicion() {
        this.amenaza = new ToOne<>(this, ObjectBoxPosicion_.amenaza);
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