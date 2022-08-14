/*package lcm.java.lists.benchmark;

import lcm.java.lists.L;

class BenchPartitionBy extends LBench<Long> {
    public BenchPartitionBy(int n) {
        super("PartitionBy", generateRandoms(n));
    }
    @Override
    public void run() {
        runFunction(l -> l.partitionBy(x -> test(x)), "partitionBy");
        runFunction(l -> {
            var lf1 = l.filter(x -> test(x));
            var lf2 = l.filter(x -> !test(x));
            return new L[] { lf1, lf2 };
        }, "Double filtering");
        runFunction(l -> {
            var lf1 = l.filter(x -> test(x));
            var lf2 = l.filter(x -> !test(x));
            return new L[] { lf1, lf2 };
        }, "Double filtering");
        runFunction(l -> l.partitionBy(x -> test(x)), "partitionBy");
        runFunction(l -> {
            var groups = l.groupBy(x -> test(x));
            return new L[] { new L<Long>(groups.get(true)), new L<Long>(groups.get(false)) };
        }, "groupBy");
        runFunction(l -> {
            var groups = l.groupBy(x -> test(x));
            return new L[] { new L<Long>(groups.get(true)), new L<Long>(groups.get(false)) };
        }, "groupBy");
    }
    @Override
    String printOutput(Object output) {
        L<?>[] arr = (L<?>[]) output;
        return "{" + super.printFirstElements(arr[0]) + ", " + super.printFirstElements(arr[1]) + "}";
    }

    private static boolean test(long x) {
        int sum = 0;
        var str = Long.valueOf(x).toString();
        for (char c : str.toCharArray()) {
            sum += Integer.valueOf(c);
        }
        return sum % 2 == 0;
    }
}*/