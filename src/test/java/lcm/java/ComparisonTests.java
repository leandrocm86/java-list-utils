package lcm.java;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import lcm.java.lists.L;

public class ComparisonTests {

    private class Foo {
        public int x;
        public Foo(int x) {
            this.x = x;
        }
        public boolean equals(Object o) {
            return o instanceof Foo ? x == ((Foo) o).x : false;
        }
        public String toString() {
            return "Foo(" + x + ")";
        }
    }
        
    @Test
    public void testRank() {
        var l = new L<Foo>(new Foo(1), new Foo(2), new Foo(3), new Foo(4), new Foo(5));
        assertEquals(new L<Foo>(new Foo(5), new Foo(4)), l.rank(2, (a, b) -> b.x - a.x));
        assertEquals(new L<Foo>(new Foo(1), new Foo(2)), l.rank(2, (a, b) -> a.x - b.x));
    }

    @Test
    public void testTopInvalidNaturalOrder() {
        var l = new L<Foo>(new Foo(1), new Foo(2), new Foo(3), new Foo(4), new Foo(5));
        assertThrows(ClassCastException.class, () -> l.highest(2));
        assertThrows(ClassCastException.class, () -> l.lowest(2));

    }

    @Test
    public void testHighest() {
        var l = new L<Integer>(1, 2, 3, 4, 5);
        assertEquals(new L<Integer>(5, 4), l.highest(2));
    }

    @Test
    public void testLowest() {
        var l = new L<Integer>(1, 2, 3, 4, 5);
        assertEquals(new L<Integer>(1, 2), l.lowest(2));
    }
    
}
