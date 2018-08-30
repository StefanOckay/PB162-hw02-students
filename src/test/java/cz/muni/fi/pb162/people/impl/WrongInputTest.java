package cz.muni.fi.pb162.people.impl;

import org.junit.Test;

import static org.junit.Assert.assertNull;

/**
 * @author tomasskopal.
 */
public class WrongInputTest extends StorageTestBase {

    @Test(expected = Exception.class)
    public void nullableFilter() {
        storage.getByFilter(null);
    }

    @Test(expected = Exception.class)
    public void nullableDataInput() {
        storage.storePeople(null);
    }


    @Test
    public void readLineWithWrongAttributes() {
        CSVReader r = new CSVReader();

        // surname
        Person person = r.parseLine(" , Tomáš:xskopal2:374549", PersonRole.STUDENT);
        assertNull("Empty surname", person);

        // name
        person = r.parseLine("Skopal, :xskopal2:374549", PersonRole.STUDENT);
        assertNull("Empty name", person);

        // name - two spaces
        person = r.parseLine("Skopal,  :xskopal2:374549", PersonRole.STUDENT);
        assertNull("Empty name (two spaces)", person);

        // login
        person = r.parseLine("Skopal,  Tomáš: :374549", PersonRole.STUDENT);
        assertNull("Empty login", person);

        // uco
        person = r.parseLine("Skopal,  Tomáš:xskopal2:", PersonRole.STUDENT);
        assertNull("Empty uco", person);

        // uco - contains letter
        person = r.parseLine("Skopal,  Tomáš:xskopal2:374549p", PersonRole.STUDENT);
        assertNull("Uco contains letter", person);
    }
}
