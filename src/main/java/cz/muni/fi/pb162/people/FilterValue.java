package cz.muni.fi.pb162.people;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tomasskopal.
 */
public class FilterValue {

    private FilterType type;
    private Set<String> values;

    /**
     * @param type - type
     * @param values - wrap values to the set
     */
    public FilterValue(FilterType type, String... values) {
        this.type = type;
        this.values = new HashSet<>(Arrays.asList(values));
    }

    public FilterType getType() {
        return type;
    }

    public Set<String> getValues() {
        return values;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FilterValue that = (FilterValue) o;

        if (type != that.type) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return type != null ? type.hashCode() : 0;
    }
}
