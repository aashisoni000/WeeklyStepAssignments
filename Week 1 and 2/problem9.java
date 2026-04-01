import java.util.*;

public class problem9 {
    static class Transaction {
        int id;
        int amount;
        String merchant;
        long timestamp;

        Transaction(int id, int amount, String merchant, long time) {
            this.id = id;
            this.amount = amount;
            this.merchant = merchant;
            this.timestamp = time;
        }
    }

    private List<Transaction> history = new ArrayList<>();

    public List<String> findTwoSum(int target) {
        Map<Integer, Transaction> map = new HashMap<>();
        List<String> pairs = new ArrayList<>();
        for (Transaction t : history) {
            int complement = target - t.amount;
            if (map.containsKey(complement)) {
                pairs.add("(id:" + map.get(complement).id + ", id:" + t.id + ")");
            }
            map.put(t.amount, t);
        }
        return pairs;
    }

    public List<String> detectDuplicates() {
        Map<String, List<Integer>> map = new HashMap<>();
        List<String> dups = new ArrayList<>();
        for (Transaction t : history) {
            String key = t.amount + "|" + t.merchant;
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(t.id);
        }
        map.forEach((k, v) -> {
            if (v.size() > 1) dups.add(k + " accounts: " + v);
        });
        return dups;
    }

    public void addTransaction(int id, int amount, String merchant) {
        history.add(new Transaction(id, amount, merchant, System.currentTimeMillis()));
    }

    public static void main(String[] args) {
        problem9 p = new problem9();
        p.addTransaction(1, 300, "Store A");
        p.addTransaction(2, 200, "Store B");
        System.out.println(p.findTwoSum(500));
    }
}
