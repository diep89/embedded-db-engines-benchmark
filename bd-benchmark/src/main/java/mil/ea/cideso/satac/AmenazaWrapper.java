package mil.ea.cideso.satac;

import mil.ea.cideso.satac.Amenaza;

public class AmenazaWrapper {
    private Amenaza amenaza;
    private boolean visible;
    private boolean leido;

    public AmenazaWrapper(Amenaza amenaza, boolean visible, boolean leido) {
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

}