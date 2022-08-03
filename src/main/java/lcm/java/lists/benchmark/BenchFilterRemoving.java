package lcm.java.lists.benchmark;

class BenchFilterRemoving extends LBench<Long> {
    public BenchFilterRemoving(int n) {
        super("FilterRemoving", generateRandoms(n));
    }
    @Override
    public void run() {
        runFunction(l -> l.filterRemoving(x -> x % 2 == 0), "filterRemoving");
        runFunction(l -> {
            var l2 = l.filter(x -> x % 2 == 0);
            l.removeAll(l2);
            return l2;
        }, "Filtering and then removing in 2 steps");
    }
}