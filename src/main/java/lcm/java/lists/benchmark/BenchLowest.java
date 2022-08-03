package lcm.java.lists.benchmark;

class BenchLowest extends LBench<Long> {
    public BenchLowest(Long[] data) {	
        super("Lowest", data);
    }
    @Override
    public void run() {
        runFunction(l -> l.lowest(5), "lowest");
        runFunction(l -> {
            return l.stream().sorted().limit(5).toList();
        }, "Sorting and then getting the first 5");
    }
}