package mil.ea.cideso.satac;

@Entity
public class ObjectBoxPerson {
    @Id
    public long id;
    @NameInDb("EDAD")
    int edad;
    @NameInDb("SEXO")
    String sexo;
    @NameInDb("TELEFONO")
    String tel;

    public ObjectBoxPerson(long id, int edad, String sexo, String tel) {
        this.id = id;
        this.edad = edad;
        this.sexo = sexo;
        this.tel = tel;
    }

    private int getEdad() {
        return edad;
    }

    private void setEdad(int newEdad) {
        edad = newEdad;
    }

    private String getSexo() {
        return sexo;
    }

    private void setSexo(String newSexo) {
        sexo = newSexo;
    }

    private int getTel() {
        return tel;
    }

    private void setTel(int newTel) {
        tel = newTel;
    }
}
