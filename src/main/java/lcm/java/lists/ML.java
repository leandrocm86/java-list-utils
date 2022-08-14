package lcm.java.lists;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class for making a multimap (Map of Lists). 
 * It abstracts a HashMap of list values, making the manipulation of elements easier.
 * The methods help to manage the lists trasparently, so we can focus on the elements.
 */
public class ML<K, V> extends HashMap<K, List<V>> {

    /**
     * Default constructor, which initializes an empty HashMap of lists internally.
     */
    public ML() {
        super();
    }

    /**
    * Constructor which initializes the ML object with the given map.
    * @see HashMap#HashMap(Map)
    */
    public ML(Map<K, List<V>> map) {
        super(map);
    }

    /**
     * Adds a new element to the map, given its key.
     * It's similar to the put method of the HashMap, but internally it stores the element in a list.
     * With this, multiple elements can be stored to the same key.
     * @param key The key of the list to add the element to.
     * @param value The value to be added associated with the key.
     */
    public void add(K key, V value) {
		List<V> list = super.get(key);
		if (list == null) {
            list = super.get(key);
            if (list == null) {
                list = new L<>();
                super.put(key, list);
            }
		}
		list.add(value);
	}

    /**
     * Adds new elements to the map to the same key.
     * @param key The key of the list to add the elements to.
     * @param values The values to be added associated with the key.
     * @see #add(Object, Object)
     */
    public void addAll(K key, Collection<V> values) {
        List<V> list = super.get(key);
        if (list == null) {
            list = new L<>();
            super.put(key, list);
        }
        list.addAll(values);
    }

    /**
     * Returns how many elements are stored associated with a given key.
     * @param key The key of the elements to be counted.
     * @return The size of the list associated with the key.
     */
    public int size(K key) {
        List<V> list = super.get(key);
        return list == null ? 0 : list.size();
    }

    /**
     * Returns the total size of the map, i.e., the total of elements added.
     * It's different from the original size method, which returns the total of keys.
     * @return The total of elements of the map (sum of the sizes of all internal lists).
     */
    public int totalSize() {
        return super.values().stream().mapToInt(Collection::size).sum();
    }

    /**
     * Returns if a given element is stored in the map with the given key.
     * @param key The key of the list to look for the element.
     * @return True if the element is stored with the given key, false otherwise.
     */
    public boolean contains(K key, V value) {
        Collection<V> list = super.get(key);
        return list != null && list.contains(value);
    }

    /**
     * Returns the top n entries of the map, according to the given comparator.
     * Optimized for not having to sort the whole map.
     * @param n the number of entries to return.
     * @param comparator The comparator to use to sort the entries.
     * @see L#rank(int, Comparator)
     */
    public L<Entry<K, List<V>>> rank(int n, Comparator<Entry<K, List<V>>> comparator) {
        // return entrySet().stream().sorted(comparator).limit(n).toList();
        var entries = new L<Entry<K, List<V>>>(super.entrySet());
        return entries.rank(n, comparator);
    }

    /**
     * Returns the top n entries of the map, in ascending order by number of elements.
     * Optimized for not having to sort the whole map.
     * @param n the number of entries to return.
     * @see ML#rank(int, Comparator)
     */
    public L<Entry<K, List<V>>> rankBySize(int n) {
        return rank(n, (entry1, entry2) -> entry2.getValue().size() - entry1.getValue().size());
    }

}
