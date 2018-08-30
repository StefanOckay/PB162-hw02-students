package cz.muni.fi.pb162.people;

import cz.muni.fi.pb162.people.impl.PersonRole;
import cz.muni.fi.pb162.people.impl.Person;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author tomasskopal.
 */
public interface PeopleStorage {

    /**
     * Read given file and fill parsed values into the storage.
     *
     * Hint: Use CSVReader
     *
     * @param file - file contains people (string contains file name with path to it)
     * @param role - specifies role of loaded people (data in file do not contains it)
     */
    void storePeople(String file, PersonRole role);

    /**
     * Save given collection into the storage. If storage already contains item this method do not override it.
     * Only update roles of specified person.
     *
     * For example: Map contains key '374549'. Linked person contains role 'STAFF'.
     * Input collection contains this person too.
     * But with role 'STUDENT'. This method update map and key '374549' will have two roles.
     *
     * @param people - collection for save into the storage
     */
    void storePeople(Collection<Person> people);

    /**
     * @see PeopleApp for example of filtering.
     *
     * @param filter - filter data according this parameter properties
     * @return people filtered by values from given filter.
     */
    Set<Person> getByFilter(Filter filter);

    /**
     * Simple getter.
     *
     * @return map just as it is. Without any filtering.
     */
    Map<Long, Person> getPeople();
}
