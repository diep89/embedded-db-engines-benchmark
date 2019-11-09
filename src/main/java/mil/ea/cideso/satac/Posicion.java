package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Posicion {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private double latitud;
    private double longitud;
    private int milisegundosFechaHora;

    public Posicion() {

    }

    public Posicion(double latitud, double longitud, int milisegundosFechaHora) {
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

}