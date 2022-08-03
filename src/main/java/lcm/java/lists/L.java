package lcm.java.lists;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * Wrapper for lists (java.util.List), adding new functionality and simplifying existing ones.
 * It decorates lists by implementing the List interface.
 * If no list is provided, an empty ArrayList is created.
 * A LinkedList may also be created by static builder methods.
 */
public class L<T> implements List<T> {
    protected List<T> list;

    /**
     * Creates a new L object.
     * By default, a new ArrayList is used.
     */
    public L() {
        this.list = createList();
    }
    
    protected List<T> createList() {
        return new ArrayList<T>();
    }

    protected L<T> createL() {
        return new L<T>();
    }

    /**
     * Creates a new L object wrapping an existing list.
     * @param list the list to wrap.
    */
    public L(List<T> list) {
        this.list = list;
    }

    /**
     * Creates a new L list with elements from other collections.
     * @param collections collections to unpack into a single list.
     */
    @SafeVarargs
    public L(Collection<T>... collections) {
        this();
        for (Collection<T> c : collections) {
            this.list.addAll(c);
        }
    }
    
    /**
     * Creates a new L object with elements from arrays.
     * @param arrays array(s) to unpack into a single list.
     */
    @SafeVarargs
    public L(T[]... arrays) {
        this();
        for (T[] a : arrays) {
            for (T e : a) {
                this.list.add(e);
            }
        }
    }

    /**
     * Creates a new L object with the given initial elements.
     * @param elements initial element(s) to put in the list.
     */
    @SafeVarargs
    public L(T... elements) {
        this();
        for (T e : elements) {
            this.list.add(e);
        }
    }

    // BEGIN OF LIST INTERFACE METHODS

    @Override
    public boolean add(T arg0) {
        return list.add(arg0);
    }

    @Override
    public void add(int arg0, T arg1) {
        list.add(arg0, arg1);
    }

    @Override
    public boolean addAll(Collection<? extends T> arg0) {
        return list.addAll(arg0);
    }

    @Override
    public boolean addAll(int arg0, Collection<? extends T> arg1) {
        return list.addAll(arg0, arg1);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public boolean contains(Object arg0) {
        return list.contains(arg0);
    }

    @Override
    public boolean containsAll(Collection<?> arg0) {
        return list.containsAll(arg0);
    }

    @Override
    public T get(int arg0) {
        return list.get(arg0);
    }

    @Override
    public int indexOf(Object arg0) {
        return list.indexOf(arg0);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return list.iterator();
    }

    @Override
    public int lastIndexOf(Object arg0) {
        return list.lastIndexOf(arg0);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int arg0) {
        return list.listIterator(arg0);
    }

    @Override
    public boolean remove(Object arg0) {
        return list.remove(arg0);
    }

    @Override
    public T remove(int arg0) {
        return list.remove(arg0);
    }

    @Override
    public boolean removeAll(Collection<?> arg0) {
        return list.removeAll(arg0);
    }

    @Override
    public boolean retainAll(Collection<?> arg0) {
        return list.retainAll(arg0);
    }

    @Override
    public T set(int arg0, T arg1) {
        return list.set(arg0, arg1);
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public L<T> subList(int arg0, int arg1) {
        return new L<T>(list.subList(arg0, arg1));
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <S> S[] toArray(S[] arg0) {
        return list.toArray(arg0);
    }


    // END OF LIST INTERFACE METHODS

    // BEGIN OF OBJECT METHODS

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public String toString() {
        return list.toString();
    }
    
    // END OF OBJECT METHODS

    // BEGIN OF UTILITY METHODS

    /**
     * Reversed version of the get method.
     * @param rindex index of the element to get (in reverse order).
     * @return the element at the given index from this list in reverse.
     */
    public T rget(int rindex) {
        return list.get(list.size() - rindex - 1);
    }

    /**
     * Selects the elements of this list that satisfy the given predicate.
     * @param predicate the predicate to use.
     * @return a new L object with the selected elements.
     */
    public L<T> filter(Predicate<T> predicate) {
        return new L<T>(list.stream().filter(predicate).toList());
    }

    /**
     * Extracts the elements of this list that satisfy the given predicate.
     * @param predicate the predicate to use.
     * @return a new L object with the removed elements from this list.
     */
    public L<T> filterRemoving(Predicate<T> predicate) {
        L<T> out = createL();
        for (Iterator<T> it = list.iterator(); it.hasNext();) {
            T e = (T) it.next();
            if (predicate.test(e)) {
                out.add(e);
                it.remove();
            }
        }
        return out;
    }

    /**
     * Splits the list into two other separate lists, according to the given predicate.
     * The first list will contain the elements that satisfy the predicate,
     * while the second will contain the elements that don't.
     * If you wish to extract elements from this list itself, consider the filterRemoving method.
     * @param predicate the predicate to use.
     * @return a pair of L objects, the first one containing the elements that satisfy the predicate,
     *        the second one containing the elements that don't.
     * @see #filterRemoving(Predicate)
     */
    @SuppressWarnings("unchecked")
    @Deprecated
    public L<T>[] partitionBy(Predicate<? super T> predicate) {
        var map = list.stream().collect(Collectors.partitioningBy(predicate));
        return new L[] { new L<T>(map.get(true)), new L<T>(map.get(false)) };
    }

    /**
     * Gives the average of the elements of this list, given a criterion.
     * @param mapper the function to extract a double from each element.
     */
    public double average(ToDoubleFunction<? super T> mapper) {
        return list.stream().mapToDouble(mapper).average().getAsDouble();
    }
    
    /**
     * Gives the sum of the elements of this list, given a criterion.
     * @param mapper the function to extract an int from each element.
     */
    public double sumInts(ToIntFunction<? super T> mapper) {
        return list.stream().mapToInt(mapper).sum();
    }

    /**
     * Gives the sum of the elements of this list, given a criterion.
     * @param mapper the function to extract a double from each element.
     */
    public double sumDoubles(ToDoubleFunction<? super T> mapper) {
        return list.stream().mapToDouble(mapper).sum();
    }

    /**
     * Gives the max of the elements of this list, given a criterion.
     * @param mapper the function to extract an int from each element.
     */
    public int maxInt(ToIntFunction<? super T> mapper) {
        return list.stream().mapToInt(mapper).max().getAsInt();
    }

    /**
     * Gives the max of the elements of this list, given a criterion.
     * @param mapper the function to extract a double from each element.
     */
    public double maxDouble(ToDoubleFunction<? super T> mapper) {
        return list.stream().mapToDouble(mapper).max().getAsDouble();
    }

    /**
     * Gives the min of the elements of this list, given a criterion.
     * @param mapper the function to extract an int from each element.
     */
    public int minInt(ToIntFunction<? super T> mapper) {
        return list.stream().mapToInt(mapper).min().getAsInt();
    }

    /**
     * Gives the min of the elements of this list, given a criterion.
     * @param mapper the function to extract a double from each element.
     */
    public double minDouble(ToDoubleFunction<? super T> mapper) {
        return list.stream().mapToDouble(mapper).min().getAsDouble();
    }

    /**
     * Gives a String representation of the elements of this list, given a criterion.
     * @param mapper the function to extract a String from each element.
     */
    public String joinStrings(Function<? super T, CharSequence> mapper, String delimiter) {
        return list.stream().map(mapper).collect(Collectors.joining(delimiter));
    }

    /**
     * Groups the elements of this list by the given criterion into a Map of Lists.
     * @param mapper the function to extract the key from each element.
     */
    public <K> Map<K, List<T>> groupBy(Function<T, K> classifier) {
        return list.stream().collect(Collectors.groupingBy(classifier));
    }

    // TODO: ML<K, T> toMap(classifier)

    /**
     * Overload of the sort(Comparator) method, with a null parameter.
     * Assumes the element type implements the Comparable interface.
     * @see java.util.List#sort(java.util.Comparator)
     */
    public void sort() {
        list.sort(null);
    }

    /**
     * Returns the top n elements of this list, according to the given comparator.
     * Optimized for not having to sort the whole list.
     * @param n the number of elements to return.
     */
    public L<T> rank(int n, Comparator<T> comparator) {
        // return list.stream().sorted(comparator).limit(n).toList();
        var tops = new ArrayList<>(list.subList(0, n));
        tops.sort(comparator);
        list.subList(n, list.size()).forEach(element -> {
            if (comparator.compare(tops.get(n-1), element) > 0) {
                tops.remove(n-1);
                int index = n-1;
                for (int j = 0; j < n-1; j++) {
                    if (comparator.compare(tops.get(j), element) > 0) {
                        index = j;
                        break;
                    }
                }
                tops.add(index, element);
            }
        });
        return new L<T>(tops);
    }

    /**
     * Returns the highest n elements of the list, sorted by natural order (desc).
     * Optimized for not having to sort the whole list.
     * Assumes the element type implements the Comparable interface.
     * @param n the number of elements to return.
     * @throws ClassCastException if the element type is not Comparable.
     * @see #top(int, Comparator)
     */
    @SuppressWarnings("unchecked")
    public L<T> highest(int n) {
        // return list.stream().sorted(Collections.reverseOrder()).limit(n).toList();
        return rank(n, (Comparator<T>) Comparator.reverseOrder());
    }

    /**
     * Returns the lowest n elements of the list, sorted by natural order (asc).
     * Optimized for not having to sort the whole list.
     * Assumes the element type implements the Comparable interface.
     * @param n the number of elements to return.
     * @throws ClassCastException if the element type is not Comparable.
     * @see #top(int, Comparator)
     */
    @SuppressWarnings("unchecked")
    public L<T> lowest(int n) {
        // return list.stream().sorted().limit(n).toList();
        return rank(n, (Comparator<T>) Comparator.naturalOrder());
    }



}
