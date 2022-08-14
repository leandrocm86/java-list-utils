package lcm.java.lists.benchmark;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class BenchGroupBy extends LBench<Long> {

    public BenchGroupBy(int n) {
        super("GROUPBY", generateNanos(n));
    }

    @Override
    void run() {
        runFunction(l -> l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(10, 14))), "classic groupBy");
        runFunction(l -> l.groupBy(x -> x.toString().substring(10, 14)), "ML Wrapper");
        runFunction(l -> l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(10, 14))), "classic groupBy");
        runFunction(l -> l.groupBy(x -> x.toString().substring(10, 14)), "ML Wrapper");
        // runFunction(l -> l.groupBySet(x -> x.toString().substring(10, 14)), "groupBySet");
        // runFunction(l -> l.groupByArray(x -> x.toString().substring(10, 14)), "groupByArray");
        // runFunction(l -> l.groupByLinked(x -> x.toString().substring(10, 14)), "groupByLinked");
    }

    @Override
    @SuppressWarnings("unchecked")
    String printOutput(Object o) {
        int maxKeysToPrint = 3;
        var map = (Map<String, Collection<Long>>) o;
        var it = map.entrySet().iterator();
        StringJoiner sj = new StringJoiner("\n    ", "\n    ", "");
        for (int i = 0;  i < maxKeysToPrint && it.hasNext(); i++) {
            var entry = it.next();
            sj.add("(" + entry.getKey() + ": " + super.printFirstElements(entry.getValue()) + ")");
        }
        return sj.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    <O> boolean equalOutputs(O o1, O o2) {
        var map1 = (Map<String, Collection<Long>>) o1;
        var map2 = (Map<String, Collection<Long>>) o2;
        var keySet1 = map1.keySet();
        var keySet2 = map2.keySet();
        if (keySet1.equals(keySet2)) {
            for (String key : keySet1) {
                var c1 = map1.get(key);
                var c2 = map2.get(key);
                if (c1 instanceof Set || c2 instanceof Set) {
                    for (var e : c1) {
                        if (!c2.contains(e)) {
                            return false;
                        }
                    }
                }
                else {
                    if (map1.get(key).size() != map2.get(key).size()) {
                        System.out.println("Different sizes for key " + key + "(" + map1.get(key).size() + " vs " + map2.get(key).size() + ")");
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    
}
