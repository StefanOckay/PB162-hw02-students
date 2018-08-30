package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author tomasskopal.
 */
public class SimpleFilteringTest extends StorageTestBase {

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

    /**
     * Empty filter means no filtering so all people should be returned.
     */
    @Test
    public void emptyFilter() {
        Filter filter = new Filter();
        Set<Person> people = storage.getByFilter(filter);
        assertEquals("All people should be returned by empty filter.", all.size(), people.size());
    }

    @Test
    public void filterByUco() {
        Person person = peopleForTest.get(0);
        FilterValue filterValue = new FilterValue(FilterType.UCO, String.valueOf(person.getUco()));
        Filter filter = new Filter(filterValue);
        Set<Person> people = storage.getByFilter(filter);

        assertEquals("Filter by one UCO must returns just one person.", 1, people.size());
        assertTrue("Result contains specified person", people.contains(person));
    }

    @Test
    public void filterByName() {
        Person person = peopleForTest.get(0);
        FilterValue filterValue = new FilterValue(FilterType.NAME, person.getName());
        Filter filter = new Filter(filterValue);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertTrue("Filtered persons name must contains filtering string.", p.getName().contains(person.getName()));
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getName().contains(person.getName())) {
                countFromAll++;
            }
        }
        assertEquals("Filtering by name must produce exact number of people.", countFromAll, people.size());
    }

    @Test
    public void filterBySurname() {
        Person person = peopleForTest.get(0);
        FilterValue filterValue = new FilterValue(FilterType.SURNAME, person.getSurname());
        Filter filter = new Filter(filterValue);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertTrue("Filtered persons surname must contains filtering string.", p.getSurname().contains(person.getSurname()));
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getSurname().contains(person.getSurname())) {
                countFromAll++;
            }
        }
        assertEquals("Filtering by surname must produce exact number of people.", countFromAll, people.size());
    }

    @Test
    public void filterByLogin() {
        Person person = peopleForTest.get(0);
        FilterValue filterValue = new FilterValue(FilterType.LOGIN, person.getLogin());
        Filter filter = new Filter(filterValue);
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains specified person", people.contains(person));
        for (Person p : people) {
            assertTrue("Filtered persons login must contains filtering string.", p.getLogin().contains(person.getLogin()));
        }

        int countFromAll = 0;
        for (Person p : all) {
            if (p.getLogin().contains(person.getLogin())) {
                countFromAll++;
            }
        }
        assertEquals("Filtering by login must produce exact number of people.", countFromAll, people.size());
    }

    @Test
    public void noOne() {
        Person person = peopleForTest.get(0);
        Person person1 = null;
        for (Person p : peopleForTest) { // get suitable second person for test
            if (!p.getSurname().contains(person.getSurname())) { // prevent partly surname match
                person1 = p;
                break;
            }
        }
        if (person1 == null) {
            System.err.println("bad input data for this test");
            return;
        }
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                new FilterValue(FilterType.UCO, String.valueOf(person.getUco())),
                new FilterValue(FilterType.SURNAME, person1.getSurname())
        );
        Set<Person> people = storage.getByFilter(filter);

        assertEquals("Filtering by incompatible uco and name have to produce empty result.", 0, people.size());
    }

    @Test
    public void fullFilter() {
        Person person = peopleForTest.get(0);
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                new FilterValue(FilterType.UCO, String.valueOf(person.getUco())),
                new FilterValue(FilterType.NAME, person.getName()),
                new FilterValue(FilterType.SURNAME, person.getSurname())
        );
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Result contains person.", people.contains(person));
        assertEquals("Full filter contains uco have to return exactly one person.", 1, people.size());
    }

    @Test
    public void likeOperation() {
        Person person = peopleForTest.get(0);
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                new FilterValue(FilterType.NAME, person.getName().substring(0, 2))
        );
        System.out.println("Using person name for filtering. Name: " + person.getName().substring(0, 2));
        Set<Person> people = storage.getByFilter(filter);

        assertTrue("Filtering by some characters from name should returns at least one person.", people.size() > 0);
        assertTrue(people.contains(person));
    }

    @Test
    public void onlyPhd() {
        Person person = phd.iterator().next();
        FilterValue type = new FilterValue(FilterType.UCO, String.valueOf(person.getUco()));

        // ----------- PHD - non empty --------------
        Filter filter = new Filter(
                FilterSource.SOURCE_PHD,
                type
        );
        Set<Person> people = storage.getByFilter(filter);

        assertTrue(people.contains(person));
        assertEquals("Filtering by role and uco have to returns just one person.", 1, people.size());

        // There is no test for student and staff because every phd is student and almost every is staff.
    }

}
