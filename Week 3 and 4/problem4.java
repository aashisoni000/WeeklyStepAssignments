import java.util.*;

public class problem4 {
    static class Asset {
        String name;
        double returnRate;

        Asset(String name, double rate) {
            this.name = name;
            this.returnRate = rate;
        }

        @Override
        public String toString() {
            return name + ":" + (int)returnRate + "%";
        }
    }

    public void mergeSort(Asset[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(Asset[] arr, int l, int m, int r) {
        Asset[] left = Arrays.copyOfRange(arr, l, m + 1);
        Asset[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i].returnRate <= right[j].returnRate) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    public void quickSortDesc(Asset[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortDesc(arr, low, pi - 1);
            quickSortDesc(arr, pi + 1, high);
        }
    }

    private int partition(Asset[] arr, int low, int high) {
        double pivot = arr[high].returnRate;
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j].returnRate >= pivot) {
                i++;
                Asset temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Asset temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void main(String[] args) {
        problem4 p = new problem4();
        Asset[] assets = {new Asset("AAPL", 12), new Asset("TSLA", 8), new Asset("GOOG", 15)};
        Asset[] m = assets.clone();
        p.mergeSort(m, 0, m.length - 1);
        System.out.println("Merge: " + Arrays.toString(m));
        Asset[] q = assets.clone();
        p.quickSortDesc(q, 0, q.length - 1);
        System.out.println("Quick (desc): " + Arrays.toString(q));
    }
}
