package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Tiempo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int epoch;

    public Tiempo() {

    }

    public Tiempo(int epoch) {
        this.epoch = epoch;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}