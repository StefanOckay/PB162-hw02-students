package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.Filter;
import cz.muni.fi.pb162.people.FilterSource;
import cz.muni.fi.pb162.people.FilterType;
import cz.muni.fi.pb162.people.FilterValue;
import cz.muni.fi.pb162.people.PeopleStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Stefan Ockay
 */
public class PeopleStorageImpl implements PeopleStorage {
    private Map<Long, Person> people = new HashMap<>();

    @Override
    public void storePeople(String file, PersonRole role) {
        CSVReader csvReader = new CSVReader();
        Set<Person> people = csvReader.readFile(file, role);
        storePeople(people);
    }

    @Override
    public void storePeople(Collection<Person> people) {
        long uco;
        Person mapPerson;
        for (Person person: people) {
            if (person == null) {
                continue;
            }
            uco = person.getUco();
            if (this.people.containsKey(uco)) {
                mapPerson = this.people.get(uco);
                mapPerson.setName(person.getName());
                mapPerson.setSurname(person.getSurname());
                mapPerson.setLogin(person.getLogin());
                mapPerson.setUco(person.getUco());
                mapPerson.addRoles(person.getRoles());
            } else {
                this.people.put(uco, person);
            }
        }
    }

    /**
     * filters the people map by uco, just an auxiliary function which should be used as first
     * when filtering people, as it returns a new set which might be modified further
     * @param ucoValues desired uco values for persons
     * @return set of persons with the specified uco
     */
    private Set<Person> filterByUCO(Set<String> ucoValues) {
        Set<Person> tmpSet = new HashSet<>();
        for(String uco: ucoValues) {
            tmpSet.add(people.get(Long.parseLong(uco)));
        }
        return tmpSet;
    }

    /**
     * filters the tmpSet by roles
     * @param tmpSet to be filtered
     * @param filterSource says what roles are allowed for the person
     */
    private void filterByRoles(Set<Person> tmpSet, FilterSource filterSource) {
        Set<PersonRole> roles = new HashSet<>();
        switch (filterSource) {
            case SOURCE_ALL:
                roles.add(PersonRole.STUDENT);
                roles.add(PersonRole.PHD);
                roles.add(PersonRole.STAFF);
                break;
            case SOURCE_PHD:
                roles.add(PersonRole.PHD);
                break;
            case SOURCE_STAFF:
                roles.add(PersonRole.STAFF);
                break;
            case SOURCE_STUDENTS:
                roles.add(PersonRole.STUDENT);
                break;
            case SOURCE_STUDENTS_AND_STAFF:
                roles.add(PersonRole.STUDENT);
                roles.add(PersonRole.STAFF);
                break;
            default:
                break;
        }
        Iterator<Person> iterator = tmpSet.iterator();
        Person person;
        boolean keep = false;
        while (iterator.hasNext()) {
            person = iterator.next();
            for (PersonRole role: roles) {
                if (person.getRoles().contains(role)) {
                    keep = true;
                    break;
                }
            }
            if (!keep) {
                iterator.remove();
            }
        }
    }

    /**
     * filters the tmpSet by name, surname or login
     * @param tmpSet to be filtered
     * @param values of the String attribute required
     * @param filterType says what are the people filtered by(uco, login, name, surname)
     */
    private void filterByString(Set<Person> tmpSet, Set<String> values, FilterType filterType) {
        boolean keep;
        String string = null;
        Iterator<Person> iterator = tmpSet.iterator();
        Person person;
        while (iterator.hasNext()) {
            person = iterator.next();
            keep = false;
            for (String value: values) {
                switch (filterType) {
                    case NAME:
                        string = person.getName();
                        break;
                    case SURNAME:
                        string = person.getSurname();
                        break;
                    case LOGIN:
                        string = person.getLogin();
                        break;
                    default:
                        break;
                }
                if (string != null && string.contains(value)) {
                    keep = true;
                    break;
                }
            }
            if (!keep) {
                iterator.remove();
            }
        }
    }

    @Override
    public Set<Person> getByFilter(Filter filter) {
        Set<FilterValue> filterValues = filter.getFilterValues();
        Set<Person> tmpSet = null;
        Iterator<FilterValue> iterator = filterValues.iterator();
        FilterValue fv;
        while (iterator.hasNext()) {
            fv = iterator.next();
            if (fv.getType() == FilterType.UCO) {
                tmpSet = filterByUCO(fv.getValues());
                filterValues.remove(fv);
                break;
            }
        }
        if (tmpSet == null) {
            tmpSet = new HashSet<>(people.values());
        }
        filterByRoles(tmpSet, filter.getFilterSource());
        for (FilterValue filterValue: filterValues) {
            filterByString(tmpSet, filterValue.getValues(), filterValue.getType());
        }
        Set<Person> result = new TreeSet<>(new PersonComparator(filter.getFilterOrder()));
        result.addAll(tmpSet);
        return Collections.unmodifiableSet(result);
    }

    @Override
    public Map<Long, Person> getPeople() {
        return Collections.unmodifiableMap(people);
    }
}
