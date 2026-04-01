import java.util.*;

public class problem5 {
    public int linearSearch(String[] logs, String target) {
        int comps = 0;
        for (int i = 0; i < logs.length; i++) {
            comps++;
            if (logs[i].equals(target)) {
                System.out.println("Linear first " + target + ": index " + i + " (" + comps + " comparisons)");
                return i;
            }
        }
        return -1;
    }

    public void binarySearch(String[] logs, String target) {
        Arrays.sort(logs);
        int low = 0, high = logs.length - 1, comps = 0, firstIdx = -1;
        while (low <= high) {
            comps++;
            int mid = low + (high - low) / 2;
            if (logs[mid].equals(target)) {
                firstIdx = mid;
                high = mid - 1;
            } else if (logs[mid].compareTo(target) < 0) low = mid + 1;
            else high = mid - 1;
        }
        int count = 0;
        for (String s : logs) if (s.equals(target)) count++;
        System.out.println("Binary " + target + ": index " + firstIdx + " (" + comps + " comparisons), count=" + count);
    }

    public static void main(String[] args) {
        problem5 p = new problem5();
        String[] logs = {"accB", "accA", "accB", "accC"};
        p.linearSearch(logs, "accB");
        p.binarySearch(logs, "accB");
    }
}
