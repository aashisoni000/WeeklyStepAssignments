import java.util.*;

public class problem6 {
    public void linearSearch(int[] bands, int target) {
        int comps = 0;
        boolean found = false;
        for (int b : bands) {
            comps++;
            if (b == target) {
                found = true;
                break;
            }
        }
        System.out.println("Linear: threshold=" + target + " -> " + (found ? "found" : "not found") + " (" + comps + " comps)");
    }

    public void binarySearchBounds(int[] bands, int target) {
        int low = 0, high = bands.length - 1;
        int floor = -1, ceiling = -1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (bands[mid] <= target) {
                floor = bands[mid];
                low = mid + 1;
            } else {
                ceiling = bands[mid];
                high = mid - 1;
            }
        }
        if (ceiling == -1 && floor != -1 && floor != target) {
            for(int b : bands) if(b > target) { ceiling = b; break; }
        }
        System.out.println("Binary floor(" + target + "): " + floor + ", ceiling: " + ceiling);
    }

    public static void main(String[] args) {
        problem6 p = new problem6();
        int[] risks = {10, 25, 50, 100};
        p.linearSearch(risks, 30);
        p.binarySearchBounds(risks, 30);
    }
}
