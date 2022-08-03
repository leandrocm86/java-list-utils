package lcm.java.lists;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * LinkedList alternative for L.
 * It works just the same as doing new L(new LinkedList<T>()).
 */
public class LL<T> extends L<T> {
    public LL() {
        super(new LinkedList<T>());
    }

    @Override
    protected List<T> createList() {
        return new LinkedList<T>();
    }

    @Override
    protected L<T> createL() {
        return new LL<T>();
    }

    /**
     * Creates a new LL list with elements from other collections.
     * @param collection collection to unpack into a single one.
     */
    @SafeVarargs
    public LL(Collection<T>... collections) {
        super(collections);
    }
    
    /**
     * Creates a new LL object with elements from arrays.
     * @param arrays array(s) to unpack into a single list.
     */
    @SafeVarargs
    public LL(T[]... arrays) {
        super(arrays);
    }

    /**
     * Creates a new LL object with the given initial elements.
     * @param elements initial element(s) to put in the list.
     */
    @SafeVarargs
    public LL(T... elements) {
        super(elements);
    }

}
