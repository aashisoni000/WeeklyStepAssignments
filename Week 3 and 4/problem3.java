import java.util.*;

public class problem3 {
    static class Trade {
        String id;
        int volume;

        Trade(String id, int volume) {
            this.id = id;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return id + ":" + volume;
        }
    }

    public void mergeSort(Trade[] arr, int l, int r) {
        if (l < r) {
            int m = l + (r - l) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private void merge(Trade[] arr, int l, int m, int r) {
        Trade[] left = Arrays.copyOfRange(arr, l, m + 1);
        Trade[] right = Arrays.copyOfRange(arr, m + 1, r + 1);
        int i = 0, j = 0, k = l;
        while (i < left.length && j < right.length) {
            if (left[i].volume <= right[j].volume) arr[k++] = left[i++];
            else arr[k++] = right[j++];
        }
        while (i < left.length) arr[k++] = left[i++];
        while (j < right.length) arr[k++] = right[j++];
    }

    public void quickSortDesc(Trade[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortDesc(arr, low, pi - 1);
            quickSortDesc(arr, pi + 1, high);
        }
    }

    private int partition(Trade[] arr, int low, int high) {
        int pivot = arr[high].volume;
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j].volume >= pivot) {
                i++;
                Trade temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        Trade temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }

    public static void main(String[] args) {
        problem3 p = new problem3();
        Trade[] trades = {new Trade("trade3", 500), new Trade("trade1", 100), new Trade("trade2", 300)};
        Trade[] mSort = trades.clone();
        p.mergeSort(mSort, 0, mSort.length - 1);
        System.out.println("MergeSort: " + Arrays.toString(mSort));
        Trade[] qSort = trades.clone();
        p.quickSortDesc(qSort, 0, qSort.length - 1);
        System.out.println("QuickSort (desc): " + Arrays.toString(qSort));
    }
}
