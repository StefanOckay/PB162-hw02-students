package cz.muni.fi.pb162.people;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Filter contains whole structure for filtering data from storage. Any property could be null.
 *
 * @author tomasskopal.
 */
public class Filter {

    private Set<FilterValue> filterValues = new HashSet<>();
    private FilterOrder filterOrder = null;
    private FilterSource filterSource = FilterSource.SOURCE_ALL; // default value is ALL

    /**
     * @param filterSource - filterSource
     * @param filterOrder - filterOrder
     * @param filterValues - filterValues
     */
    public Filter(FilterSource filterSource, FilterOrder filterOrder, FilterValue... filterValues) {
        this(filterSource, filterValues);
        this.filterOrder = filterOrder;
    }

    /**
     * @param filterSource - filterSource
     * @param filterValues - filterValues
     */
    public Filter(FilterSource filterSource, FilterValue... filterValues) {
        this(filterValues);
        this.filterSource = filterSource;
    }

    /**
     * @param filterValues - filterValues
     */
    public Filter(FilterValue... filterValues) {
        this();
        this.filterValues = new HashSet<>(Arrays.asList(filterValues));
    }

    /**
     * Create new filter object
     */
    public Filter() {}


    public Set<FilterValue> getFilterValues() {
        return filterValues;
    }

    public FilterOrder getFilterOrder() {
        return filterOrder;
    }

    public FilterSource getFilterSource() {
        return filterSource;
    }

    public void setFilterSource(FilterSource filterSource) {
        this.filterSource = filterSource;
    }
}
