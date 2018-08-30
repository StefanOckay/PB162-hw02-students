package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author: Jana Zahradnickova,  UCO 433598
 * @version: 3. 12. 2015
 */
public class MyOrderingTests extends StorageTestBase {

    private static final List<Person> peopleForTest = new ArrayList<>();
    private static Set<Person> all;

    private static Set<Person> phd;

    @BeforeClass
    public static void setUpBefore() {
        CSVReader reader = new CSVReader();

        phd = reader.readFile("files/phd.csv", PersonRole.PHD);
        all = reader.readFile("files/all.csv", PersonRole.STUDENT);

        int i = 0;
        for (Person p : all) {
            if (i % 100 == 0) {
                peopleForTest.add(p);
            }
            i++;
        }
    }

    /*
        @Test
        public void orderByUco(){
            Person person1 = peopleForTest.get(17);
            Person person2 = peopleForTest.get(21);
            Person person3 = peopleForTest.get(10);
            Filter filter = new Filter(
                    FilterSource.SOURCE_ALL,
                    FilterOrder.UCO,
                    new FilterValue(FilterType.UCO, String.valueOf(person3.getUco()),
                                                    String.valueOf(person2.getUco()),
                                                    String.valueOf(person1.getUco()))

            );
            Set<Person> people = storage.getByFilter(filter);


            Person previous = null;
            for (Person p : people) {
                if (previous == null) {
                    previous = p;
                    continue;
                }
                assertTrue("spatne razeni podle UCO", previous.compareTo(p) < 0);
                previous = p;
            }
        }
    */

    @Test
    public void orderByUcoAll() {
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                FilterOrder.UCO
        );
        Set<Person> people = storage.getByFilter(filter);
        Person previous = null;
        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }
            assertTrue("spatne razeni podle UCO", previous.getUco() < p.getUco());
            previous = p;
        }
        assertEquals("Someone is missing", people.size(), all.size());
    }

/*
    @Test
    public void orderBySurname(){
        Person person1 = peopleForTest.get(17);
        Person person2 = peopleForTest.get(21);
        Person person3 = peopleForTest.get(10);
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                FilterOrder.SURNAME,
                new FilterValue(FilterType.UCO, String.valueOf(person3.getUco()),
                        String.valueOf(person2.getUco()),
                        String.valueOf(person1.getUco()))

        );
        Set<Person> people = storage.getByFilter(filter);

        Person previous = null;
        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }
            if (previous.getSurname().equals(p.getSurname())){
                assertTrue("spatne razeni podle SURNAME pri shode prijemni", previous.getSurname().compareTo(p.getSurname()) <= 0);
                assertTrue("spatne razeni podle SURNAME pri shode prijmeni", previous.compareTo(p) < 0);
            }
            else {
                assertTrue("spatne razeni podle SURNAME", previous.getSurname().compareTo(p.getSurname()) < 0);
            }
            previous = p;
        }

    }
*/

    @Test
    public void orderBySurnameAll() {
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                FilterOrder.SURNAME
        );
        Set<Person> people = storage.getByFilter(filter);
        Person previous = null;

        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }

            if (previous.getSurname().equals(p.getSurname())) {
                assertTrue("spatne razeni podle SURNAME pri shode prijemni", previous.getSurname().compareTo(p.getSurname()) <= 0);
                assertTrue("spatne razeni podle SURNAME pri shode prijmeni", previous.getUco() < p.getUco());
            } else {
                assertTrue("spatne razeni podle SURNAME", previous.getSurname().compareTo(p.getSurname()) < 0);
            }
            previous = p;
        }
        assertEquals("Someone is missing", people.size(), all.size());
    }


    @Test
    public void orderByNameAll() {
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                FilterOrder.NAME
        );
        Set<Person> people = storage.getByFilter(filter);
        Person previous = null;
        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }
            if (previous.getName().equals(p.getName())) {
                assertTrue("spatne razeni podle NAME", previous.getName().compareTo(p.getName()) <= 0);
                assertTrue("spatne razeni podle NAME", previous.getUco() < p.getUco());
            } else
                assertTrue("spatne serazovani podle NAME", previous.getName().compareTo(p.getName()) < 0);
            previous = p;
        }
        assertEquals("Someone is missing", people.size(), all.size());
    }


    @Test
    public void orderBySurnameNameAll() {
        Filter filter = new Filter(
                FilterSource.SOURCE_ALL,
                FilterOrder.SURNAME_AND_NAME
        );
        Set<Person> people = storage.getByFilter(filter);
        Person previous = null;
        for (Person p : people) {
            if (previous == null) {
                previous = p;
                continue;
            }
            if (previous.getSurname().equals(p.getSurname())) {
                if (previous.getName().equals(p.getName())) {
                    assertTrue("spatne razeni podle SURNAME_AND_NAME pri shode prijmeni i jmena (Line 1)", previous.getSurname().compareTo(p.getSurname()) <= 0);
                    assertTrue("spatne razeni podle SURNAME_AND_NAME pri shode prijmeni i jmena (Line 2)", previous.getName().compareTo(p.getName()) <= 0);
                    assertTrue("spatne razeni podle SURNAME_AND_NAME pri shode prijmeni i jmena (Line 3)", previous.getUco() < p.getUco());
                } else {
                    assertTrue("spatne razeni podle SURNAME_AND_NAME pri shode prijmeni (Line 1)", previous.getSurname().compareTo(p.getSurname()) <= 0);
                    assertTrue("spatne razeni podle SURNAME_AND_NAME pri shode prijmeni (Line 2)", previous.getName().compareTo(p.getName()) < 0);
                }
            } else {
                assertTrue("spatne razeni podle SURNAME_AND_NAME", previous.getSurname().compareTo(p.getSurname()) < 0);
            }
            previous = p;
        }
        assertEquals("Someone is missing", people.size(), all.size());
    }
}
