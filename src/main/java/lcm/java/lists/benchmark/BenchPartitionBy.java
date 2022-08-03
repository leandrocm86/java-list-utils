package lcm.java.lists.benchmark;

import lcm.java.lists.L;

class BenchPartitionBy extends LBench<Long> {
    public BenchPartitionBy(int n) {
        super("PartitionBy", generateRandoms(n));
    }
    @Override
    public void run() {
        runFunction(l -> l.partitionBy(x -> x % 2 == 0), "partitionBy");
        runFunction(l -> {
            var lf1 = l.filter(x -> x % 2 == 0);
            var lf2 = l.filter(x -> x % 2 != 0);
            return new L[] { lf1, lf2 };
        }, "Double filtering");
        runFunction(l -> {
            var groups = l.groupBy(x -> x % 2 == 0);
            return new L[] { new L<Long>(groups.get(true)), new L<Long>(groups.get(false)) };
        }, "groupBy");
    }
    @Override
    String printOutput(Object output) {
        L<?>[] arr = (L<?>[]) output;
        return "{" + super.printList(arr[0]) + ", " + super.printList(arr[1]) + "}";
    }
}