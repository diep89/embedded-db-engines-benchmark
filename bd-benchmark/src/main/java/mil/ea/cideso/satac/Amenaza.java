package mil.ea.cideso.satac;

import mil.ea.cideso.satac.Equipamiento;
import mil.ea.cideso.satac.Tiempo;
import mil.ea.cideso.satac.Posicion;
import mil.ea.cideso.satac.Informante;

public class Amenaza {
    private int id;
    private Tiempo tiempo;
    private int codigoSimbolo;
    private Posicion posicion;
    private int radioAccion;
    private int identificacion;
    private int tamanios;
    private Equipamiento equipamiento;
    private Informante informante;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Tiempo tiempo) {
        this.tiempo = tiempo;
    }

    public int getCodigoSimbolo() {
        return codigoSimbolo;
    }

    public void setCodigoSimbolo(int codigoSimbolo) {
        this.codigoSimbolo = codigoSimbolo;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public int getRadioAccion() {
        return radioAccion;
    }

    public void setRadioAccion(int radioAccion) {
        this.radioAccion = radioAccion;
    }

    public int getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(int identificacion) {
        this.identificacion = identificacion;
    }

    public int getTamanios() {
        return tamanios;
    }

    public void setTamanios(int tamanios) {
        this.tamanios = tamanios;
    }

    public Equipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(Equipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }

    public Informante getInformante() {
        return informante;
    }

    public void setInformante(Informante informante) {
        this.informante = informante;
    }

    public Amenaza(int id, Tiempo tiempo, int codigoSimbolo, Posicion posicion, int radioAccion, int identificacion,
            int tamanios, Equipamiento equipamiento, Informante informante) {
        this.id = id;
        this.tiempo = tiempo;
        this.codigoSimbolo = codigoSimbolo;
        this.posicion = posicion;
        this.radioAccion = radioAccion;
        this.identificacion = identificacion;
        this.tamanios = tamanios;
        this.equipamiento = equipamiento;
        this.informante = informante;
    }

}