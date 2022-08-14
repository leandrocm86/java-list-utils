package lcm.java.lists.benchmark;

import java.util.Collection;
import java.util.Random;
import java.util.StringJoiner;

import lcm.java.lists.L;
import lcm.java.lists.LL;

abstract class LBench<T> {
    interface LFunction<T, O> {
        public O exec(L<T> l);
    }
    interface LConsumer<T> {
        public void exec(L<T> l);
    }

    String name;
    T[] data;
    L<T> lastList = null;
    Object lastOutput = null;

    LBench(String name, T[] data) {
        this.name = name;
        this.data = data;
    }

    static Long[] generateNanos(int size) {
        Long[] arr = new Long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = System.nanoTime() / 100;
        }
        return arr;
    }
    static Long[] generateSequence(int size) {
        Long[] arr = new Long[size];
        for (int i = 0; i < size; i++) {
            arr[i] = Long.valueOf(i);
        }
        return arr;
    }
    static Long[] generateRandoms(int size) {
        return generateRandoms(size, 0);
    }
    static Long[] generateRandoms(int size, int minValue) {
        Long[] arr = new Long[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextLong(minValue, Long.MAX_VALUE);
        }
        return arr;
    }
    static void printSeparator() {
        System.out.println("-----------------------------------------------------");
    }

    void addList(L<T> list) {
        if (lastList != null && !lastList.equals(list))
            throw new AssertionError("Modified list not like the previous!");
        lastList = new L<T>(list);
    }
    
    <O> void addOutput(O output) {
        if (lastOutput != null)
            if (!equalOutputs(lastOutput, output)) {
                System.out.println("===== ERROR: DIFFERENT OUTPUTS! =====");
                System.out.println("PREVIOUS: " + printOutput(lastOutput));
                System.out.println("NOW: " + printOutput(output));
                throw new AssertionError("Output not like the previous!");
            }
        lastOutput = output;
    }
    <O> boolean equalOutputs(O o1, O o2) {
        if (!o1.equals(o2)) {
            if (o1.getClass().isArray() && o2.getClass().isArray()) {
                var arr1 = (Object[]) o1;
                var arr2 = (Object[]) o2;
                for (int i = 0; i < arr1.length; i++) {
                    if (!arr1[i].equals(arr2[i]))
                        return false;
                }
            }
            else {
                return false;
            }
        }
        return true;
    }

    <O> void runFunction(LFunction<T, O> lf, String title) {
        printSeparator();
        var l = new L<T>(data);
        System.gc();
        long start = System.currentTimeMillis();
        var ol = lf.exec(l);
        System.out.println(String.format("[%s] Time for %s: %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("Output: " + printOutput(ol));
        addOutput(ol);
        System.out.println("List: " + printFirstElements(l));
        addList(l);
        var ll = new LL<T>(data);
        System.gc();
        start = System.currentTimeMillis();
        var oll = lf.exec(ll);
        System.out.println(String.format("[%s] Time for %s (LL): %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("Output: " + printOutput(oll));
        addOutput(oll);
        System.out.println("List: " + printFirstElements(ll));
        addList(ll);
    }
    
    void runVoid(LConsumer<T> lc, String title) {
        printSeparator();
        var l = new L<T>(data);
        System.gc();
        long start = System.currentTimeMillis();
        lc.exec(l);
        System.out.println(String.format("[%s] Time for %s: %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("List: " + printFirstElements(l));
        addList(l);
        var ll = new LL<T>(data);
        System.gc(); 
        start = System.currentTimeMillis();
        lc.exec(ll);
        System.out.println(String.format("[%s] Time for %s (LL): %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("List: " + printFirstElements(ll));
        addList(ll);
    }

    String printFirstElements(Collection<?> l) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        var it = l.iterator();
        for (int i = 0; i < 5 && it.hasNext(); i++) {
            sj.add(String.valueOf(it.next()));
        }
        return sj.toString();
    }

    String printOutput(Object o) {
        if (o instanceof Collection) {
            return printFirstElements((Collection<?>) o);
        } else {
            return String.valueOf(o);
        }
    }

    abstract void run();
}
