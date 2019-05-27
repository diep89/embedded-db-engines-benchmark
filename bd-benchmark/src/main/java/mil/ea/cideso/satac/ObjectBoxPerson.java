package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class ObjectBoxPerson {

    @Id
    public long id;

    @NameInDb("EDAD")
    public int edad;

    @NameInDb("SEXO")
    public String sexo;

    @NameInDb("TELEFONO")
    public int tel;

    public ObjectBoxPerson(long id, int edad, String sexo, int tel) {
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
