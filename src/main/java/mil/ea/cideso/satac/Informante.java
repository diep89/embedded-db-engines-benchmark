package mil.ea.cideso.satac;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Informante {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Informante() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}