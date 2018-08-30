package cz.muni.fi.pb162.people.impl;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author tomasskopal.
 */
public class CSVReaderTest extends StorageTestBase {

    @Test
    public void readLine() {
        CSVReader r = new CSVReader();
        Person person = r.parseLine("Skopal, Tomáš:xskopal2:374549", PersonRole.STUDENT);

        assertNotNull("Person should be successfully parsed from valid line.", person);
        assertEquals("Person must contains valid name.", "Tomáš", person.getName());
        assertEquals("Person must contains valid surname.", "Skopal", person.getSurname());
        assertEquals("Person must contains valid login.", "xskopal2", person.getLogin());
        assertEquals("Person must contains valid uco.", Long.valueOf(374549), Long.valueOf(person.getUco()));
        assertEquals("Person must contains only one role by default.", 1, person.getRoles().size());
        assertTrue("Person role must be equal to input one.", person.getRoles().contains(PersonRole.STUDENT));
    }

    @Test
    public void readFiles() {
        CSVReader r = new CSVReader();
        int numberOfAllPersons = r.readFile("files/all.csv", PersonRole.STUDENT).size();

        assertEquals("All persons must be parsed from valid file.", numberOfAllPersons, storage.getPeople().size());
    }

    @Test
    public void storePeople() {
        CSVReader r = new CSVReader();
        PeopleStorageImpl storage1 = new PeopleStorageImpl();

        storage1.storePeople("files/students.csv", PersonRole.STUDENT);
        int numberOfAllPersons = r.readFile("files/students.csv", PersonRole.STUDENT).size();

        assertEquals("Function storePeople must store every person.", numberOfAllPersons, storage1.getPeople().size());
    }

    @Test
    public void storePeopleWithRoles() {
        Person person = new Person();
        person.setName("Jan");
        person.setSurname("Novak");
        person.setUco(1111L);
        person.setLogin("login");
        person.addRoles(new HashSet<>(Arrays.asList(PersonRole.PHD)));

        Person person1 = new Person();
        person1.setName("Jan");
        person1.setSurname("Novak");
        person1.setUco(1111L);
        person1.setLogin("login");
        person1.addRoles(new HashSet<>(Arrays.asList(PersonRole.STAFF)));

        Set<Person> people = new HashSet<>(Arrays.asList(person));

        PeopleStorageImpl storage1 = new PeopleStorageImpl();
        storage1.storePeople(people);

        people.clear();
        people.add(person1);
        storage1.storePeople(people);

        assertEquals("Storing same person with two roles must produce only one object.", 1, storage1.getPeople().size());
        assertTrue("Stored person must contains both roles.",
                storage1.getPeople()
                .get(1111L)
                .getRoles()
                .containsAll(
                        Arrays.asList(PersonRole.PHD, PersonRole.STAFF)
                )
        );
    }
}
