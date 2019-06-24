package mil.ea.cideso.satac;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.NameInDb;

@Entity
public class ObjectBoxPerson {

    @Id
    private long id;

    @NameInDb("EDAD")
    private int edad;

    @NameInDb("SEXO")
    private String sexo;

    @NameInDb("TELEFONO")
    private int tel;

    public ObjectBoxPerson(long id, int edad, String sexo, int tel) {
        this.id = id;
        this.edad = edad;
        this.sexo = sexo;
        this.tel = tel;
    }

    public ObjectBoxPerson() {
    }

    public long getId() {
        return id;
    }

    public void setId(long newId) {
        id = newId;
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
