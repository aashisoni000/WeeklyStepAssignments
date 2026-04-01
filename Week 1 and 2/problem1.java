import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class problem1 {
    private Map<String, Long> userRegistry = new ConcurrentHashMap<>();
    private Map<String, Integer> attemptTracker = new ConcurrentHashMap<>();

    public problem1() {

        userRegistry.put("john_doe", 101L);
        userRegistry.put("jane_smith", 102L);
        userRegistry.put("admin", 103L);
        attemptTracker.put("admin", 10543);
    }

    public boolean checkAvailability(String username) {

        attemptTracker.merge(username, 1, Integer::sum);

        return !userRegistry.containsKey(username);
    }

    public List<String> suggestAlternatives(String username) {
        List<String> suggestions = new ArrayList<>();

        String dotVariation = username.contains("_") ? username.replace("_", ".") : username + ".user";
        if (!userRegistry.containsKey(dotVariation)) {
            suggestions.add(dotVariation);
        }

        int suffix = 1;
        while (suggestions.size() < 3) {
            String candidate = username + suffix;
            if (!userRegistry.containsKey(candidate)) {
                suggestions.add(candidate);
            }
            suffix++;
        }

        return suggestions.subList(0, 3);
    }

    public String getMostAttempted() {
        String topUser = "";
        int max = -1;

        for (Map.Entry<String, Integer> entry : attemptTracker.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                topUser = entry.getKey();
            }
        }
        return topUser + " (" + max + " attempts)";
    }

    public static void main(String[] args) {
        problem1 system = new problem1();

        String test1 = "john_doe";
        System.out.println("checkAvailability(\"" + test1 + "\") → " + system.checkAvailability(test1) + " (taken)");
        System.out.println("suggestAlternatives(\"" + test1 + "\") → " + system.suggestAlternatives(test1));

        String test2 = "jane_doe";
        System.out.println("checkAvailability(\"" + test2 + "\") → " + system.checkAvailability(test2) + " (available)");

        System.out.println("getMostAttempted() → " + system.getMostAttempted());
    }
}
