package mil.ea.cideso.satac;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table
public class AmenazaWrapper {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private boolean visible;
    private boolean leido;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Amenaza amenaza;

    public AmenazaWrapper() {

    }

    public AmenazaWrapper(int id, Amenaza amenaza, boolean visible, boolean leido) {
        this.id = id;
        this.amenaza = amenaza;
        this.visible = visible;
        this.leido = leido;
    }

    public Amenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(Amenaza amenaza) {
        this.amenaza = amenaza;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}