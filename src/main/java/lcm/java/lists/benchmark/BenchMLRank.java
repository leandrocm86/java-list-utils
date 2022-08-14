package lcm.java.lists.benchmark;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;

import lcm.java.lists.L;
import lcm.java.lists.ML;

public class BenchMLRank extends LBench<Long> {
    public BenchMLRank(int n) {
        super("ML RANK", generateRandoms(n, 10000));
    }
    
    @Override
    public void run() {
        runFunction(l -> {
            Map<String, List<Long>> map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(0, 5)));
            return map.entrySet().stream().sorted(getEntryComparator()).limit(3).toList();
        }, "Pure streams");
        runFunction(l -> {
            ML<String, Long> map = l.groupBy(x -> x.toString().substring(0, 5));
            return map.rank(3, getEntryComparator());
        }, "RANK");
        runFunction(l -> {
            var map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(0, 5)));
            var treemap = new TreeMap<String, Collection<Long>>(getKeyComparator(map));
            treemap.putAll(map);
            return treemap.entrySet().stream().limit(3).toList();
        }, "Treemap with stream limit");
        runFunction(l -> {
            var map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(0, 5)));
            var treemap = new TreeMap<String, Collection<Long>>(getKeyComparator(map));
            treemap.putAll(map);
            var it = treemap.entrySet().iterator();
            var out = new L<Entry<String, Collection<Long>>>();
            for (int i = 0; i < 3; i++) {
                out.add(it.next());
            }
            return out;
        }, "Treemap with iterator");
        runFunction(l -> {
            ML<String, Long> map = l.groupBy(x -> x.toString().substring(0, 5));
            return map.rank(3, getEntryComparator());
        }, "RANK");
        runFunction(l -> {
            Map<String, List<Long>> map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(0, 5)));
            return map.entrySet().stream().sorted(getEntryComparator()).limit(3).toList();
        }, "Pure streams");
    }

    private static Comparator<String> getKeyComparator(Map<String, List<Long>> map) {
        return (k1, k2) -> {
            var list = map.get(k1);
            var list2 = map.get(k2);
            Long l1 = list.stream().mapToLong(x -> x.longValue()).max().getAsLong();
            Long l2 = list2.stream().mapToLong(x -> x.longValue()).max().getAsLong();
            return l2.compareTo(l1);
        };
    }

    private static Comparator<Entry<String, List<Long>>> getEntryComparator() {
        return (e1, e2) -> {
            Long l1 = e1.getValue().stream().mapToLong(x -> x.longValue()).max().getAsLong();
            Long l2 = e2.getValue().stream().mapToLong(x -> x.longValue()).max().getAsLong();
            return l2.compareTo(l1);
        };
    }

    @Override
    @SuppressWarnings("unchecked")
    <O> boolean equalOutputs(O o1, O o2) {
        var c1 = (Collection<Long>) o1;
        var c2 = (Collection<Long>) o2;
        return c1.size() == c2.size() && c1.containsAll(c2);
    }

    @Override
    @SuppressWarnings("unchecked")
    String printOutput(Object o) {
        var l = (List<Entry<String, Collection<Long>>>) o;
        return "\n" + l.stream().limit(3)
            .map(e -> e.getKey() + ": " + e.getValue().stream().mapToLong(x -> x.longValue()).max().getAsLong())
            .collect(Collectors.joining("\n"));
    }

    
}
