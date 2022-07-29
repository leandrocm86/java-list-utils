package lcm.java;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

public class InstantiatingTests {

    @Test
    public void testDefaultConstructor() {
        var l = new L<String>();
        assertEquals(0, l.size());
    }

    @Test
    public void testConstructorWithList() {
        var al = new ArrayList<String>();
        al.add("a");
        var l = new L<String>(al);
        assertEquals("a", l.get(0));
    }

    @Test
    public void testConstructorWithLists() {
        var al1 = new ArrayList<String>();
        al1.add("a");
        var al2 = new ArrayList<String>();
        al2.add("b");
        var l = new L<String>(al1, al2);
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
    }

    @Test
    public void testConstructorWithArray() {
        var ar = new String[] { "a", "b" };
        var l = new L<String>(ar);
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
    }

    @Test
    public void testConstructorWithArrays() {
        var ar1 = new String[] { "a", "b" };
        var ar2 = new String[] { "c" };
        var l = new L<String>(ar1, ar2);
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
        assertEquals("c", l.get(2));
    }

    @Test
    public void testConstructorWithInitialElement() {
        var l = new L<String>("a");
        assertEquals("a", l.get(0));
    }

    @Test
    public void testConstructorWithInitialElements() {
        var l = new L<String>("a", "b");
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
    }

    @Test
    public void testConstructorWithCollection() {
        var set = new java.util.HashSet<String>();
        set.add("a");
        set.add("b");
        var l = new L<String>(set);
        assertEquals("a", l.get(0));
        assertEquals("b", l.get(1));
    }
}