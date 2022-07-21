package lcm.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Wrapper for lists (java.util.List), adding new functionality and simplifying existing ones.
 * It decorates lists by implementing the List interface.
 * If no list is provided, an empty ArrayList is created.
 * A LinkedList may also be created by static builder methods.
 */
public class L<T> implements List<T> {
    private List<T> list;

    /**
     * Creates a new L object.
     * By default, a new ArrayList is used.
     */
    public L() {
        this.list = new ArrayList<T>();
    }
    
    /**
     * Creates a new L object wrapping an existing list.
     * @param list the list to wrap.
    */
    public L(List<T> list) {
        this.list = list;
    }

    /**
     * Creates a new L list with elements from other lists.
     * @param lists lists to unpack into a single one.
     */
    @SafeVarargs
    public L(List<T>... lists) {
        this();
        for (List<T> l : lists) {
            this.list.addAll(l);
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
    public List<T> subList(int arg0, int arg1) {
        return list.subList(arg0, arg1);
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <S> S[] toArray(S[] arg0) {
        return list.toArray(arg0);
    }

}
