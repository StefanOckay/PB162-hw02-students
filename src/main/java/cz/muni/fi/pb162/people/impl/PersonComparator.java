package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.FilterOrder;

import java.util.Comparator;

/**
 * @author Stefan Ockay
 */
public class PersonComparator implements Comparator<Person> {
    private FilterOrder order;

    public PersonComparator(FilterOrder order) {
        if (order == null) {
            this.order = FilterOrder.UCO;
        } else {
            this.order = order;
        }
    }

    public PersonComparator() {
        this.order = FilterOrder.UCO;
    }

    /**
     * compares two Persons by order assigned in constructor,
     * if the persons are the same by primary comparison,
     * native compareTo is used
     * @param person
     * @param t1
     * @return
     */
    @Override
    public int compare(Person person, Person t1) {
        int result;
        switch (order) {
            case NAME:
                result = person.getName().compareTo(t1.getName());
                if (result == 0) {
                    break;
                }
                return result;
            case SURNAME:
                result = person.getSurname().compareTo(t1.getSurname());
                if (result == 0) {
                    break;
                }
                return result;
            case SURNAME_AND_NAME:
                int surNameDiff = person.getSurname().compareTo(t1.getSurname());
                if (surNameDiff == 0) {
                    result = person.getName().compareTo(t1.getName());
                    if (result == 0) {
                        break;
                    }
                    return result;
                }
                return surNameDiff;
            default:
                break;
        }
        return person.compareTo(t1);
    }
}
