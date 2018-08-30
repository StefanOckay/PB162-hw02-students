package cz.muni.fi.pb162.people.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stefan Ockay
 */
public class Person implements Comparable<Person> {
    private String name;
    private String surname;
    private String login;
    /**
     * unique attribute for each Person
     */
    private long uco;
    private Set<PersonRole> roles = new HashSet<>();

    public Person() {
    }

    public Person(String name, String surname, String login, long uco) {
        this.uco = uco;
        this.name = name;
        this.surname = surname;
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getLogin() {
        return login;
    }

    public long getUco() {
        return uco;
    }

    public Set<PersonRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setUco(long uco) {
        this.uco = uco;
    }

    public void addRole(PersonRole role) {
        if (role != null) {
            roles.add(role);
        }
    }

    public void addRoles(Collection<PersonRole> roles) {
        if (roles == null) {
            return;
        }
        for (PersonRole role: roles) {
            addRole(role);
        }
    }

    @Override
    public String toString() {
        String s = String.format("Name: %s\nSurname: %s\nLogin: %s\nUCO: %d\nRoles:", name, surname, login, uco);
        StringBuilder builder = new StringBuilder();
        builder.append(s);
        for (PersonRole role: roles) {
            s = " " + role.toString();
            builder.append(s);
        }
        builder.append("\n");
        return builder.toString();
    }

    /**
     * two Persons equal if they have the same uco
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof Person)) {
            return false;
        }
        Person p = (Person)object;
        return uco == p.getUco();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(uco);
    }

    /**
     * native comparison done with uco
     * @param person
     * @return
     */
    @Override
    public int compareTo(Person person) {
        long diff = uco - person.getUco();
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }
}
