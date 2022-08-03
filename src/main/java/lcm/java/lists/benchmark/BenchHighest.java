package lcm.java.lists.benchmark;

import java.util.Collections;

class BenchHighest extends LBench<Long> {
    public BenchHighest(Long[] data) {
        super("Highest", data);
    }
    @Override
    public void run() {
        runFunction(l -> l.highest(5), "highest");
        runFunction(l -> {
            return l.stream().sorted(Collections.reverseOrder()).limit(5).toList();
        }, "Sorting and then getting the first 5");
    }
}