package lcm.java.lists.benchmark;

import java.util.Comparator;

class BenchRank extends LBench<Long> {
    final Comparator<Long> comparator;
    public BenchRank(Long[] data, int digitsToCompare) {	
        super("Rank", data);
        this.comparator = createComparator(digitsToCompare);
    }
    @Override
    public void run() {
        runFunction(l -> l.rank(5, comparator), "rank");
        runFunction(l -> {
            return l.stream().sorted(comparator).limit(5).toList();
        }, "Sorting and then getting the first 5");
    }
    private Comparator<Long> createComparator(int digitsToCompare) {
        return (a, b) -> {
            var aString = a.toString();
            var bString = b.toString();
            while (aString.length() < digitsToCompare) {
                aString = "0" + aString;
            }
            while (bString.length() < digitsToCompare) {
                bString = "0" + bString;
            }
            return bString.substring(bString.length() - digitsToCompare)
            .compareTo(aString.substring(aString.length() - digitsToCompare));
        };
    }
}