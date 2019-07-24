package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import mil.ea.cideso.satac.Equipamiento;
import mil.ea.cideso.satac.Tiempo;
import mil.ea.cideso.satac.Posicion;
import mil.ea.cideso.satac.Informante;

@Entity
@Table
public class Amenaza {

    @Id
    private int id;
    @OneToOne
    private Tiempo tiempo;
    private int codigoSimbolo;
    @OneToOne
    private Posicion posicion;
    private int radioAccion;
    private int identificacion;
    private int tamanios;
    @OneToOne
    private Equipamiento equipamiento;
    @OneToOne
    private Informante informante;
    @OneToOne
    private AmenazaWrapper amenazaWrapper;

    public Amenaza() {

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

    public AmenazaWrapper getAmenazaWrapper() {
        return amenazaWrapper;
    }

    public void setAmenazaWrapper(AmenazaWrapper amenazaWrapper) {
        this.amenazaWrapper = amenazaWrapper;
    }

}