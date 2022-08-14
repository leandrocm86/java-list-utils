package lcm.java.lists.benchmark;

import java.util.Collection;
import java.util.stream.Collectors;

public class BenchMLTotalSize extends LBench<Long> {
    public BenchMLTotalSize(int n) {
        super("GROUPBY", generateNanos(n));
    }
    
    @Override
    public void run() {
        runFunction(l -> {
            var map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(9, 13)));
            return map.values().stream().mapToInt(Collection::size).sum();
        }, "normal count");
        runFunction(l -> {
            var map = l.groupBy(x -> x.toString().substring(9, 13));
            return map.totalSize();
        }, "totalSize");
        runFunction(l -> {
            var map = l.stream().collect(Collectors.groupingBy(x -> x.toString().substring(9, 13)));
            return map.values().stream().mapToInt(Collection::size).sum();
        }, "normal count");
        runFunction(l -> {
            var map = l.groupBy(x -> x.toString().substring(9, 13));
            return map.totalSize();
        }, "totalSize");
    }
    
}
