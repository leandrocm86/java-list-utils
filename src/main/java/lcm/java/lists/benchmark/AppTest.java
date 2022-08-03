package lcm.java.lists.benchmark;

public class AppTest {
    public static void main( String[] args )
    {
        // System.out.println("Ranking from nanos...");
        // new BenchRank(LBench.generateNanos(10000000), 5).run();

        // System.out.println("Ranking from randoms...");
        // new BenchRank(LBench.generateRandoms(10000000), 5).run();

        // System.out.println("Highest from randoms...");
        // new BenchHighest(LBench.generateRandoms(10000000)).run();

        // System.out.println("Highest from sequence...");
        // new BenchHighest(LBench.generateSequence(10000000)).run();
        
        // System.out.println("Lowest from randoms...");
        // new BenchLowest(LBench.generateRandoms(10000000)).run();

        // System.out.println("Lowest from sequence...");
        // new BenchLowest(LBench.generateSequence(10000000)).run();

        // System.out.println("Benchmarking filterRmoving...");
        // new BenchFilterRemoving(10000).run();

        System.out.println("Benchmarking partitionBy...");
        new BenchPartitionBy(5000000).run();
    }
}
