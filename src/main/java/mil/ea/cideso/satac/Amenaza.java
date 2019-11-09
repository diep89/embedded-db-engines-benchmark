package mil.ea.cideso.satac;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class Amenaza {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Tiempo tiempo;
    private int codigoSimbolo;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Posicion posicion;
    private int radioAccion;
    private int identificacion;
    private int tamanios;
    @OneToMany(targetEntity = Equipamiento.class, mappedBy = "amenaza", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Equipamiento> equipamientoList = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Informante informante;

    public Amenaza() {

    }

    public Amenaza(Tiempo tiempo, int codigoSimbolo, Posicion posicion, int radioAccion, int identificacion,
            int tamanios, List<Equipamiento> equipamientoList, Informante informante) {
        this.tiempo = tiempo;
        this.codigoSimbolo = codigoSimbolo;
        this.posicion = posicion;
        this.radioAccion = radioAccion;
        this.identificacion = identificacion;
        this.tamanios = tamanios;
        this.equipamientoList = equipamientoList;
        this.informante = informante;
    }

    public void addEquipamientoItem(Equipamiento equip) {
        equip.setAmenaza(this);
        this.equipamientoList.add(equip);
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

    public Informante getInformante() {
        return informante;
    }

    public void setInformante(Informante informante) {
        this.informante = informante;
    }

    public List<Equipamiento> getEquipamientoList() {
        return equipamientoList;
    }

    public void setEquipamientoList(List<Equipamiento> equipamientoList) {
        this.equipamientoList = equipamientoList;
    }

}