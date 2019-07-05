package mil.ea.cideso.satac;

import mil.ea.cideso.satac.Db4oAmenaza;

public class Db4oAmenazaWrapper {
    private Db4oAmenaza amenaza;
    private boolean visible;
    private boolean leido;

    public Db4oAmenazaWrapper(Db4oAmenaza amenaza, boolean visible, boolean leido) {
        this.amenaza = amenaza;
        this.visible = visible;
        this.leido = leido;
    }

    public Db4oAmenaza getAmenaza() {
        return amenaza;
    }

    public void setAmenaza(Db4oAmenaza amenaza) {
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

}