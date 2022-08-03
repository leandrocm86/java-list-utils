package lcm.java.lists.benchmark;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringJoiner;

import lcm.java.lists.L;
import lcm.java.lists.LL;

abstract class LBench<T> {
    interface LFunction<T> {
        public Object exec(L<T> l);
    }
    interface LConsumer<T> {
        public void exec(L<T> l);
    }

    String name;
    T[] data;
    ArrayList<L<T>> lists = new ArrayList<>();
    ArrayList<Object> outputs = new ArrayList<>();

    LBench(String name, T[] data) {
        this.name = name;
        this.data = data;
    }

    void addList(L<T> list) {
        if (!lists.isEmpty())
            assertEquals(lists.get(lists.size() - 1), list, "Modified list not like the previous!");
        lists.add(new L<T>(list));
    }
    void addOutput(Object output) {
        if (!outputs.isEmpty())
            assertEquals(outputs.get(outputs.size() - 1), output, "Output not like the previous!");
        outputs.add(output);
    }
    static void assertEquals(Object o1, Object o2, String errorMsg) {
        if (!o1.equals(o2)) {
            if (o1.getClass().isArray() && o2.getClass().isArray()) {
                var arr1 = (Object[]) o1;
                var arr2 = (Object[]) o2;
                for (int i = 0; i < arr1.length; i++) {
                    assertEquals(arr1[i], arr2[i], errorMsg);
                }
            }
            else {
                throw new AssertionError(errorMsg);
            }
        }
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
        Long[] arr = new Long[size];
        Random rand = new Random();
        for (int i = 0; i < size; i++) {
            arr[i] = rand.nextLong();
        }
        return arr;
    }

    void printSeparator() {
        System.out.println("-----------------------------------------------------");
    }

    void runFunction(LFunction<T> lf, String title) {
        printSeparator();
        var l = new L<T>(data);
        long start = System.currentTimeMillis();
        var ol = lf.exec(l);
        System.out.println(String.format("[%s] Time for %s: %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("Output: " + printOutput(ol));
        addOutput(ol);
        System.out.println("List: " + printList(l));
        addList(l);
        var ll = new LL<T>(data);
        start = System.currentTimeMillis();
        var oll = lf.exec(ll);
        System.out.println(String.format("[%s] Time for %s (LL): %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("Output: " + printOutput(oll));
        addOutput(oll);
        System.out.println("List: " + printList(ll));
        addList(ll);
    }
    
    void runVoid(LConsumer<T> lc, String title) {
        printSeparator();
        var l = new L<T>(data);
        long start = System.currentTimeMillis();
        lc.exec(l);
        System.out.println(String.format("[%s] Time for %s: %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("List: " + printList(l));
        addList(l);
        var ll = new LL<T>(data);
        start = System.currentTimeMillis();
        lc.exec(ll);
        System.out.println(String.format("[%s] Time for %s (LL): %dms", name, title, System.currentTimeMillis() - start));
        System.out.println("List: " + printList(ll));
        addList(ll);
    }

    String printList(L<?> l) {
        StringJoiner sj = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < l.size() && i < 5; i++) {
            sj.add(String.valueOf(l.get(i)));
        }
        return sj.toString();
    }

    String printOutput(Object o) {
        if (o instanceof L) {
            return printList((L<?>) o);
        } else {
            return String.valueOf(o);
        }
    }

    abstract void run();

}
