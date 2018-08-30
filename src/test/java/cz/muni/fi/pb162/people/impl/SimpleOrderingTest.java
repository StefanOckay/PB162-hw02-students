package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertTrue;

/**
 * @author tomasskopal.
 */
public class SimpleOrderingTest extends StorageTestBase {

    @Test
    public void nativeOrdering() {
        Filter filter = new Filter();
        Set<Person> people = storage.getByFilter(filter);
        Person previous = null;
        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }
            assertTrue("Test for ascending order.", previous.getUco() < p.getUco());
            previous = p;
        }
    }
}
