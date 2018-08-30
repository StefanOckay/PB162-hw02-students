package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import cz.muni.fi.pb162.people.FilterSource;
import cz.muni.fi.pb162.people.FilterType;
import cz.muni.fi.pb162.people.FilterValue;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jana Zahradnickova,  UCO 433598
 * @version: 4. 12. 2015
 */
public class UnmodificableTest extends StorageTestBase {

    private static final List<Person> peopleForTest = new ArrayList<>();
    private static Set<Person> all;
    private static Set<Person> phd;

    @BeforeClass
    public static void setUpBefore() {
        CSVReader reader = new CSVReader();
        all = reader.readFile("files/all.csv", PersonRole.STUDENT);
        phd = reader.readFile("files/phd.csv", PersonRole.PHD);

        int i = 0;
        for (Person p : all) {
            if (i % 100 == 0) {
                peopleForTest.add(p);
            }
            i++;
        }
    }

    @Test
    public void unmodifiable1() {
        Person person = phd.iterator().next();
        FilterValue type = new FilterValue(FilterType.UCO, String.valueOf(person.getUco()));

        // ----------- PHD - non empty --------------
        Filter filter = new Filter(
                FilterSource.SOURCE_PHD,
                type
        );
        Set<Person> people = storage.getByFilter(filter);


        assertTrue(people.contains(person));
        assertEquals(people.size(), 1);

        try {
            Person oneMore = new Person("Pepa", "Zbytecny", "xzbytecny", Long.parseLong("123456789"));
            oneMore.addRole(PersonRole.STUDENT);

            people.add(oneMore);
            assertEquals("getByFilter() vraci modifikovatelnou kolekci", 1, people.size());
        } catch (UnsupportedOperationException ex) {
            // ok
        }
    }

    @Test
    public void unmodifiable2() {
        Map<Long, Person> people = storage.getPeople();
        int peopleSize = people.size();
        try {
            Person oneMore = new Person("Pepa", "Zbytecny", "xzbytecny", Long.parseLong("123456789"));
            oneMore.addRole(PersonRole.STUDENT);

            people.put(oneMore.getUco(), oneMore);
            assertEquals("getPeople() vraci modifikovatelnou kolekci", peopleSize, people.size());
        } catch (UnsupportedOperationException ex) {
            // ok
        }
    }
}
