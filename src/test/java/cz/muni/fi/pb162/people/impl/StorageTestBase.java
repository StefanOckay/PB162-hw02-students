package cz.muni.fi.pb162.people.impl;

import cz.muni.fi.pb162.people.PeopleApp;
import cz.muni.fi.pb162.people.PeopleStorage;
import org.junit.Before;

/**
 * @author jcechace
 */
public abstract class StorageTestBase {

    protected PeopleStorage storage;

    @Before
    public void prepareStorage() {
        storage = PeopleApp.createStorage();
    }
}
