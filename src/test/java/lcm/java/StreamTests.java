package lcm.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lcm.java.lists.L;

public class StreamTests {

    @Test
    public void testFilter() {
        var l = new L<Integer>(1, 2, 3, 4, 5);
        var filtered = l.filter(i -> i % 2 == 0);
        assertEquals(new L<Integer>(2, 4), filtered);
    }

    @Test
    public void testPartitionBy() {
        var l = new L<Integer>(1, 2, 3, 4, 5);
        var partitioned = l.partitionBy(i -> i % 2 == 0);
        assertEquals(new L<Integer>(2, 4), partitioned[0]);
        assertEquals(new L<Integer>(1, 3, 5), partitioned[1]);
    }

    @Test
    public void testIntReductions() {
        var l = new L<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        assertEquals(5.5, l.average(e -> e), 0);
        assertEquals(55, l.sumInts(e -> e), 0);
        assertEquals(10, l.maxInt(e -> e), 0);
        assertEquals(1, l.minInt(e -> e), 0);
    }

    @Test
    public void testDoubleReductions() {
        var l = new L<Double>(0.5, 1.0, 1.5, 2.0, 2.5, 3.0, 3.5, 4.0, 4.5, 5.0);
        assertEquals(2.75, l.average(e -> e), 0);
        assertEquals(55, l.sumDoubles(e -> e * 2), 0);
        assertEquals(5, l.maxDouble(e -> e), 0);
        assertEquals(0.5, l.minDouble(e -> e), 0);
    }

    @Test
    public void testJoinStrings() {
        var l = new L<String>("a", "b", "c", "d", "e");
        assertEquals("a, b, c, d, e", l.joinStrings(e -> e, ", "));
    }
    
}
