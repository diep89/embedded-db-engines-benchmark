package mil.ea.cideso.satac;

public class Db4oPerson {
    private int edad;
    private String sexo;
    private int tel;

    public Db4oPerson(int edad, String sexo, int tel) {
        this.edad = edad;
        this.sexo = sexo;
        this.tel = tel;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int newEdad) {
        edad = newEdad;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String newSexo) {
        sexo = newSexo;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int newTel) {
        tel = newTel;
    }
}