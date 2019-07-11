package mil.ea.cideso.satac;

import mil.ea.cideso.satac.ObjectBoxEquipamiento;
import mil.ea.cideso.satac.ObjectBoxTiempo;
import mil.ea.cideso.satac.ObjectBoxPosicion;
import mil.ea.cideso.satac.ObjectBoxInformante;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ObjectBoxAmenaza {
    @Id
    private long id;
    private ObjectBoxTiempo tiempo;
    private int codigoSimbolo;
    private ObjectBoxPosicion posicion;
    private int radioAccion;
    private int identificacion;
    private int tamanios;
    private ObjectBoxEquipamiento equipamiento;
    private ObjectBoxInformante informante;

    public ObjectBoxAmenaza(int id, ObjectBoxTiempo tiempo, int codigoSimbolo, ObjectBoxPosicion posicion,
            int radioAccion, int identificacion, int tamanios, ObjectBoxEquipamiento equipamiento,
            ObjectBoxInformante informante) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public ObjectBoxTiempo getTiempo() {
        return tiempo;
    }

    public void setTiempo(ObjectBoxTiempo tiempo) {
        this.tiempo = tiempo;
    }

    public int getCodigoSimbolo() {
        return codigoSimbolo;
    }

    public void setCodigoSimbolo(int codigoSimbolo) {
        this.codigoSimbolo = codigoSimbolo;
    }

    public ObjectBoxPosicion getPosicion() {
        return posicion;
    }

    public void setPosicion(ObjectBoxPosicion posicion) {
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

    public ObjectBoxEquipamiento getEquipamiento() {
        return equipamiento;
    }

    public void setEquipamiento(ObjectBoxEquipamiento equipamiento) {
        this.equipamiento = equipamiento;
    }

    public ObjectBoxInformante getInformante() {
        return informante;
    }

    public void setInformante(ObjectBoxInformante informante) {
        this.informante = informante;
    }

}