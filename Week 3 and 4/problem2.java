import java.util.*;

public class problem2 {
    static class Client {
        String name;
        int riskScore;
        double balance;

        Client(String name, int riskScore, double balance) {
            this.name = name;
            this.riskScore = riskScore;
            this.balance = balance;
        }

        @Override
        public String toString() {
            return name + ":" + riskScore;
        }
    }

    public void bubbleSortAsc(Client[] arr) {
        int n = arr.length;
        int swaps = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j].riskScore > arr[j + 1].riskScore) {
                    Client temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swaps++;
                }
            }
        }
        System.out.println("Bubble (asc): " + Arrays.toString(arr) + " // Swaps: " + swaps);
    }

    public void insertionSortDesc(Client[] arr) {
        int n = arr.length;
        for (int i = 1; i < n; i++) {
            Client key = arr[i];
            int j = i - 1;
            while (j >= 0 && (arr[j].riskScore < key.riskScore || (arr[j].riskScore == key.riskScore && arr[j].balance < key.balance))) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
        System.out.println("Insertion (desc): " + Arrays.toString(arr));
    }

    public static void main(String[] args) {
        problem2 p = new problem2();
        Client[] clients = {
                new Client("clientC", 80, 1000),
                new Client("clientA", 20, 2000),
                new Client("clientB", 50, 1500)
        };
        p.bubbleSortAsc(clients.clone());
        p.insertionSortDesc(clients);
        System.out.print("Top 3 risks: ");
        for (int i = 0; i < Math.min(3, clients.length); i++) System.out.print(clients[i] + (i < 2 ? ", " : ""));
        System.out.println();
    }
}
