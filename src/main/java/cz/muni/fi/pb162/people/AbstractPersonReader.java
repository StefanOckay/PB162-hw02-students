package cz.muni.fi.pb162.people;

import cz.muni.fi.pb162.people.impl.Person;
import cz.muni.fi.pb162.people.impl.PersonRole;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomasskopal.
 */
public abstract class AbstractPersonReader {

    /**
     * Read given file, parse it into Person objects. These objects are returned as a set.
     *
     * @param pathToFile - path to file
     * @param type       - specifies role of loaded people (data in file do not contains it)
     * @return set of successfully loaded people
     */
    public Set<Person> readFile(String pathToFile, PersonRole type) {

        String line;
        Set<Person> result = new HashSet<>();

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(pathToFile), "UTF8"))) {

            while ((line = br.readLine()) != null) {

                Person p = parseLine(line, type);
                result.add(p);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Parse line.
     *
     * @param line - contains data about person. See for example phd.csv for data structure
     * @param type - person role
     * @return instance of Person class filled with values from input parameters or null if line has wrong format.
     * Format is described in readme.
     */
    protected abstract Person parseLine(String line, PersonRole type);
}
