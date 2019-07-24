package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Posicion {
    @Id
    private int id;
    private double latitud;
    private double longitud;
    private int milisegundosFechaHora;
    @OneToOne
    private Amenaza amenaza;

    public Posicion() {

    }

    public Posicion(int id, double latitud, double longitud, int milisegundosFechaHora) {
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Amenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(Amenaza amenaza) {
        this.amenaza = amenaza;
    }

}