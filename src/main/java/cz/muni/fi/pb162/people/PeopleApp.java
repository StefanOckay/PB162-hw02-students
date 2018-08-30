package cz.muni.fi.pb162.people;

import cz.muni.fi.pb162.people.impl.PersonRole;
import cz.muni.fi.pb162.people.impl.PeopleStorageImpl;
import cz.muni.fi.pb162.people.impl.Person;

import java.util.Set;

/**
 * Example app demonstrate filtering by all possible parameters.
 *
 * @author tomasskopal.
 */
public class PeopleApp {

    protected static final String STUDENTS_CSV_FILE = "files/students.csv";
    protected static final String PHD_CSV_FILE = "files/phd.csv";
    protected static final String STAFF_CSV_FILE = "files/staff.csv";

    /**
     * Create storage contains data from all files.
     *
     * @return - storage contains map of people
     */
    public static PeopleStorage createStorage() {
        PeopleStorage storage = new PeopleStorageImpl();
        storage.storePeople(STUDENTS_CSV_FILE, PersonRole.STUDENT);
        storage.storePeople(PHD_CSV_FILE, PersonRole.PHD);
        storage.storePeople(STAFF_CSV_FILE, PersonRole.STAFF);
        return storage;
    }

    /**
     * Example shows filter which returns two concrete people.
     *
     * @param args - not used in this program
     */
    public static void main(String[] args) {
        PeopleStorage storage = createStorage();

        Filter filter = new Filter(
                FilterSource.SOURCE_STUDENTS,
                FilterOrder.SURNAME_AND_NAME,
                new FilterValue(FilterType.UCO, "374549", "94"),
                new FilterValue(FilterType.NAME, "Tom"),
                new FilterValue(FilterType.SURNAME, "Skop", "Pitner")
        );

        Set<Person> people = storage.getByFilter(filter);
        for (Person p : people) {
            System.out.println(p.toString());
        }

        System.out.println("Done");
    }
}
