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

    public Db4oTiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(Db4oTiempo tiempo) {
        this.tiempo = tiempo;
    }

    public int getCodigoSimbolo() {
        return codigoSimbolo;
    }

    public void setCodigoSimbolo(int codigoSimbolo) {
        this.codigoSimbolo = codigoSimbolo;
    }

    public Db4oPosicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Db4oPosicion posicion) {
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

    public Db4oEquipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(Db4oEquipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }

    public Db4oInformante getInformante() {
        return informante;
    }

    public void setInformante(Db4oInformante informante) {
        this.informante = informante;
    }

    public Db4oAmenaza(int id, Db4oTiempo tiempo, int codigoSimbolo, Db4oPosicion posicion, int radioAccion,
            int identificacion, int tamanios, Db4oEquipamiento equipamiento, Db4oInformante informante) {
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