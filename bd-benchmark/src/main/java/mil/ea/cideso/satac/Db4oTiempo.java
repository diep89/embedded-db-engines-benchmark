package mil.ea.cideso.satac;

public class Db4oTiempo {

    private int epoch;

    public Db4oTiempo(int epoch) {
        this.epoch = epoch;
    }

    public int getEpoch() {
        return epoch;
    }

    public void setEpoch(int epoch) {
        this.epoch = epoch;
    }

}