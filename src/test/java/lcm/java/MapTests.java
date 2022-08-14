package lcm.java;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import lcm.java.lists.L;
import lcm.java.lists.ML;


public class MapTests {

    private ML<String, Integer> createTestMap() {
        var map = new ML<String, Integer>();
        map.add("a", 1);
        map.add("b", 2); map.add("b", 3);
        map.add("c", 4); map.add("c", 5); map.add("c", 6);
        map.add("d", 7); map.add("d", 8); map.add("d", 9); map.add("d", 10);
        map.add("e", 11); map.add("e", 12); map.add("e", 13); map.add("e", 14); map.add("e", 15);
        return map;
    }

    @Test
    public void testTotalSize() {
        var map = createTestMap();
        assertEquals(5, map.size());
        assertEquals(15, map.totalSize());
    }

    @Test
    public void testContains() {
        var map = createTestMap();
        assertEquals(true, map.contains("a", 1));
        assertEquals(true, map.contains("b", 3));
        assertEquals(true, map.contains("c", 5));
        assertEquals(true, map.contains("d", 8));
        assertEquals(true, map.contains("e", 11));
        assertEquals(false, map.contains("a", 2));
        assertEquals(false, map.contains("b", 4));
        assertEquals(false, map.contains("c", 10));
        assertEquals(false, map.contains("d", 13));
        assertEquals(false, map.contains("e", 17));
    }

    @Test
    public void testRankBySize() {
        var map = createTestMap();
        var top2 = map.rankBySize(2);
        assertEquals("e", top2.get(0).getKey());
        assertEquals(new L<Integer>(7, 8, 9, 10), top2.get(1).getValue());
    }

    @Test
    public void testGroupBy() {
        var l = new L<Integer>(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        var map1 = l.groupBy(x -> x % 2);
        var map2 = l.groupBy(x -> x % 3);
        assertEquals(new L<Integer>(2, 4, 6, 8, 10), map1.get(0));
        assertEquals(new L<Integer>(1, 3, 5, 7, 9), map1.get(1));
        assertEquals(new L<Integer>(3, 6, 9), map2.get(0));
        assertEquals(new L<Integer>(1, 4, 7, 10), map2.get(1));
        assertEquals(new L<Integer>(2, 5, 8), map2.get(2));
    } 
    
}
