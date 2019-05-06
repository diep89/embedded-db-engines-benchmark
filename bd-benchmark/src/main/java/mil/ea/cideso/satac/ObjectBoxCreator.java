/*
 * package mil.ea.cideso.satac;
 * 
 * import io.objectbox.Box; import io.objectbox.BoxStore;
 * 
 * public class ObjectBoxCreator {
 * 
 * BoxStore person = MyObjectBox.builder().name("objectbox-persons-db").build();
 * Box<ObjectBoxPerson> box = person.boxFor(ObjectBoxPerson.class);
 * 
 * int edad = 1; String sexo = "M"; int tel = 11111111;
 * 
 * box.put(new ObjectBoxPerson(0, edad, sexo, tel));
 * 
 * System.out.println(box.count() + " persons in ObjectBox database: "); for
 * (ObjectBoxPerson person : box.getAll()) { System.out.println(person); }
 * 
 * /* long id = box.put(person); // Create Person person = box.get(id); // Read
 * person.setLastName("Black"); box.put(person); // Update box.remove(person);
 * // Delete
 * 
 * }
 */