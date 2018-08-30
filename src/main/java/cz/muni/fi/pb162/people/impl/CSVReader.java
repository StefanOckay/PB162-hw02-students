package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.AbstractPersonReader;

/**
 * @author Stefan Ockay
 */
public class CSVReader extends AbstractPersonReader {

    /**
     * checks if the specified string contains at least one letter
     * @param s to be checked
     * @return true if the string contains letter char
     */
    private boolean hasLetter(String s) {
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (Character.isLetter(c)) {
                return true;
            }
        }
        return false;
    }

    /**
     * parses line read from a file and stores the result in new Person(Surname, name, login, uco)
     * @param line - contains data about person. See for example phd.csv for data structure
     * @param type - person role
     * @return person containing all the parsed info,
     * null if surname, name, login are empty or don't contain any letter or if uco can't be parsed into long type
     */
    @Override
    protected Person parseLine(String line, PersonRole type) {
        String[] fields0 = line.split(", ", 2);
        String surname = fields0[0];
        if (surname.length() < 1 || !hasLetter(surname)) {
            return null;
        }
        String[] fields1 = fields0[1].split(":", 3);
        String name = fields1[0];
        if (name.length() < 1 || !hasLetter(name)) {
            return null;
        }
        String login = fields1[1];
        if (login.length() < 1 || !hasLetter(login)) {
            return null;
        }
        long uco;
        try {
            uco = Long.parseLong(fields1[2]);
            if (uco < 0) {
                return null;
            }
        } catch (NumberFormatException ex) {
            return null;
        }
        Person p = new Person();
        p.setName(name);
        p.setSurname(surname);
        p.setLogin(login);
        p.setUco(uco);
        p.addRole(type);
        return p;
    }
}
