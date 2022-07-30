package lcm.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

public class AppTest {
    public static void main( String[] args )
    {
        var app = new AppTest();
        // app.benchmarkRank(5, generateNanos(10000000), 3);
        // app.benchmarkRank(5, generateRandoms(10000000), 3);
        // System.out.println("Highest from sequence...");
        // app.benchmarkHighest(5, generateSequence(10000000));
        // System.out.println("Lowest from sequence...");
        // app.benchmarkLowest(5, generateSequence(10000000));
        // System.out.println("Lowest from random...");
        // app.benchmarkLowest(5, generateRandoms(10000000));

        app.benchmarkFilterRemoving(10000);
    }

    private void printSeparator() {
        System.out.println("-----------------------------------------------------");
    }

    private static Long[] generateNanos(int size) {
        Long[] arr = new Long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = System.nanoTime() / 100;
        }
        return arr;
    }

    private static Long[] generateSequence(int size) {
        Long[] arr = new Long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Long.valueOf(i);
        }
        return arr;
    }

    private static Long[] generateRandoms(int size) {
        Long[] arr = new Long[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextLong();
        }
        return arr;
    }

    private static void validate(boolean assertion) {
        if (!assertion) {
            throw new RuntimeException("Assertion failed");
        }
    }

    private Comparator<Long> createComparator(int digitsToCompare) {
        return (a, b) -> {
            var aString = a.toString();
            var bString = b.toString();
            return bString.substring(bString.length() - digitsToCompare)
            .compareTo(aString.substring(aString.length() - digitsToCompare));
        };
    }

    private void benchmarkRank(int n, Long[] arr, int comparatorLength) {
        var l = new L<Long>(arr);
        var ll = new LL<>(arr);
        var arrayList = new ArrayList<Long>(Arrays.asList(arr));
        var linkedList = new LinkedList<Long>(Arrays.asList(arr));
        var comparator = createComparator(comparatorLength);
        long start = System.nanoTime();
        var topLL = ll.rank(n, comparator);
        System.out.println("Time for LL: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var top = l.rank(n, comparator);
        System.out.println("Time for L: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var topArray = arrayList.stream().sorted(comparator).limit(n).toList();
        System.out.println("Time for arraylist: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        // Collect
        var topLinked = linkedList.stream().sorted(comparator).limit(n).toList();
        System.out.println("Time for linkedlist: " + (System.nanoTime() - start) / 1000000 + " ms");
        
        System.out.println("Result ll: " + topLL);
        System.out.println("Result: " + top);
        System.out.println("Result array: " + topArray);
        System.out.println("Result linked: " + topLinked);

        printSeparator();
    }

    private void benchmarkHighest(int n, Long[] arr) {
        var l = new L<Long>(arr);
        var ll = new LL<>(arr);
        var arrayList = new ArrayList<Long>(Arrays.asList(arr));
        var linkedList = new LinkedList<Long>(Arrays.asList(arr));
        long start = System.nanoTime();
        var topLL = ll.highest(n);
        System.out.println("Time for LL: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var top = l.highest(n);
        System.out.println("Time for L: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var topArray = arrayList.stream().sorted(Collections.reverseOrder()).limit(n).toList();

        System.out.println("Time for arraylist: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        Collections.sort(linkedList, Collections.reverseOrder());
        var topLinked = linkedList.subList(0, n);
        System.out.println("Time for linkedlist: " + (System.nanoTime() - start) / 1000000 + " ms");
        
        System.out.println("Result ll: " + topLL);
        System.out.println("Result: " + top);
        System.out.println("Result array: " + topArray);
        System.out.println("Result linked: " + topLinked);

        printSeparator();
    }

    private void benchmarkLowest(int n, Long[] arr) {
        var l = new L<Long>(arr);
        var ll = new LL<>(arr);
        var arrayList = new ArrayList<Long>(Arrays.asList(arr));
        var linkedList = new LinkedList<Long>(Arrays.asList(arr));
        long start = System.nanoTime();
        var topLL = ll.lowest(n);
        System.out.println("Time for LL: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var top = l.lowest(n);
        System.out.println("Time for L: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var topArray = arrayList.stream().sorted().limit(n).toList();
        System.out.println("Time for arraylist: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        Collections.sort(linkedList);
        var topLinked = linkedList.subList(0, n);
        System.out.println("Time for linkedlist: " + (System.nanoTime() - start) / 1000000 + " ms");
        
        System.out.println("Result ll: " + topLL);
        System.out.println("Result: " + top);
        System.out.println("Result array: " + topArray);
        System.out.println("Result linked: " + topLinked);

        printSeparator();
    }

    private void benchmarkFilterRemoving(int n) {
        var l1 = new L<>(generateSequence(n));
        var l2 = new L<>(generateSequence(n));
        var ll1 = new LL<>(generateSequence(n));
        var ll2 = new LL<>(generateSequence(n));
        
        long start = System.nanoTime();
        var l3 = l1.filter(x -> x % 2 == 0);
        l1.removeAll(l3);
        System.out.println("Time for filtering L in 2 steps: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var l4 = l2.filterRemoving(x -> x % 2 == 0);
        System.out.println("Time for filterRemoving L: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var ll3 = ll1.filter(x -> x % 2 == 0);
        ll1.removeAll(ll3);
        System.out.println("Time for filtering LL in 2 steps: " + (System.nanoTime() - start) / 1000000 + " ms");
        start = System.nanoTime();
        var ll4 = ll2.filterRemoving(x -> x % 2 == 0);
        System.out.println("Time for filterRemoving LL: " + (System.nanoTime() - start) / 1000000 + " ms");
        
        validate(l1.equals(l2) && l3.equals(l4) && ll1.equals(ll2) && ll3.equals(ll4));

        printSeparator();
    }
}
