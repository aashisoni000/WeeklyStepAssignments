import java.util.*;

public class problem1 {
    static class Transaction {
        String id;
        double fee;
        String ts;

        Transaction(String id, double fee, String ts) {
            this.id = id;
            this.fee = fee;
            this.ts = ts;
        }

        @Override
        public String toString() {
            return id + ":" + fee + (ts != null ? "@" + ts : "");
        }
    }

    public void bubbleSort(List<Transaction> list) {
        int n = list.size();
        int passes = 0;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            passes++;
            boolean swapped = false;
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j).fee > list.get(j + 1).fee) {
                    Collections.swap(list, j, j + 1);
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) break;
        }
        System.out.println("BubbleSort: " + list + " // " + passes + " passes, " + swaps + " swaps");
    }

    public void insertionSort(List<Transaction> list) {
        int n = list.size();
        for (int i = 1; i < n; i++) {
            Transaction key = list.get(i);
            int j = i - 1;
            while (j >= 0 && (list.get(j).fee > key.fee || (list.get(j).fee == key.fee && list.get(j).ts.compareTo(key.ts) > 0))) {
                list.set(j + 1, list.get(j));
                j--;
            }
            list.set(j + 1, key);
        }
        System.out.println("InsertionSort (fee+ts): " + list);
    }

    public void flagOutliers(List<Transaction> list) {
        List<String> outliers = new ArrayList<>();
        for (Transaction t : list) {
            if (t.fee > 50.0) outliers.add(t.id);
        }
        System.out.println("High-fee outliers: " + (outliers.isEmpty() ? "none" : outliers));
    }

    public static void main(String[] args) {
        problem1 p = new problem1();
        List<Transaction> txns = new ArrayList<>(Arrays.asList(
                new Transaction("id1", 10.5, "10:00"),
                new Transaction("id2", 25.0, "09:30"),
                new Transaction("id3", 5.0, "10:15")
        ));
        p.bubbleSort(new ArrayList<>(txns));
        p.insertionSort(txns);
        p.flagOutliers(txns);
    }
}
