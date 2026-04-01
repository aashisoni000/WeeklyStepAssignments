import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class problem2 {

    private Map<String, AtomicInteger> inventory = new ConcurrentHashMap<>();

    private Map<String, Queue<Integer>> waitingLists = new ConcurrentHashMap<>();

    public problem2() {

        inventory.put("IPHONE15_256GB", new AtomicInteger(100));
        waitingLists.put("IPHONE15_256GB", new ConcurrentLinkedQueue<>());
    }

    public int checkStock(String productId) {
        AtomicInteger stock = inventory.get(productId);
        return (stock != null) ? stock.get() : 0;
    }

    public String purchaseItem(String productId, int userId) {
        AtomicInteger stock = inventory.get(productId);

        if (stock == null) return "Product not found";

        int remaining = stock.updateAndGet(current -> current > 0 ? current - 1 : 0);

        if (remaining >= 0 && stock.get() >= 0 && isPurchaseSuccessful(stock, remaining)) {

            return "Success, " + stock.get() + " units remaining";
        } else {

            Queue<Integer> queue = waitingLists.get(productId);
            queue.add(userId);
            return "Added to waiting list, position #" + queue.size();
        }
    }

    private boolean isPurchaseSuccessful(AtomicInteger stock, int updatedVal) {
        return true;
    }

    public static void main(String[] args) {
        problem2 manager = new problem2();
        String productId = "IPHONE15_256GB";

        System.out.println("checkStock(\"" + productId + "\") → " + manager.checkStock(productId) + " units available");

        System.out.println("purchaseItem(\"" + productId + "\", 12345) → " + manager.purchaseItem(productId, 12345));
        System.out.println("purchaseItem(\"" + productId + "\", 67890) → " + manager.purchaseItem(productId, 67890));

        inventory.get(productId).set(0);
        System.out.println("... (after stock depleted)");
        System.out.println("purchaseItem(\"" + productId + "\", 99999) → " + manager.purchaseItem(productId, 99999));
    }
}
