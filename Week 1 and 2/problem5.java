import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class problem5 {

    private Map<String, AtomicInteger> pageViews = new ConcurrentHashMap<>();

    private Map<String, Set<String>> uniqueVisitors = new ConcurrentHashMap<>();

    private Map<String, AtomicInteger> trafficSources = new ConcurrentHashMap<>();

    public void processEvent(String url, String userId, String source) {

        pageViews.computeIfAbsent(url, k -> new AtomicInteger(0)).incrementAndGet();

        uniqueVisitors.computeIfAbsent(url, k -> ConcurrentHashMap.newKeySet()).add(userId);

        trafficSources.computeIfAbsent(source, k -> new AtomicInteger(0)).incrementAndGet();
    }

    public void getDashboard() {
        System.out.println("\n--- Real-Time Dashboard (Updated) ---");

        List<Map.Entry<String, AtomicInteger>> topPages = pageViews.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().get(), e1.getValue().get()))
                .limit(10)
                .collect(Collectors.toList());

        System.out.println("Top Pages:");
        for (int i = 0; i < topPages.size(); i++) {
            String url = topPages.get(i).getKey();
            int views = topPages.get(i).getValue().get();
            int uniques = uniqueVisitors.get(url).size();
            System.out.printf("%d. %s - %d views (%d unique)\n", (i + 1), url, views, uniques);
        }

        int totalTraffic = trafficSources.values().stream().mapToInt(AtomicInteger::get).sum();
        System.out.println("\nTraffic Sources:");
        trafficSources.forEach((source, count) -> {
            double percentage = (count.get() * 100.0) / totalTraffic;
            System.out.printf("%s: %.1f%%  ", source, percentage);
        });
        System.out.println("\n------------------------------------");
    }

    public static void main(String[] args) throws InterruptedException {
        problem5 analytics = new problem5();

        analytics.processEvent("/article/breaking-news", "user_123", "Google");
        analytics.processEvent("/article/breaking-news", "user_456", "Facebook");
        analytics.processEvent("/article/breaking-news", "user_123", "Google");
        analytics.processEvent("/sports/championship", "user_789", "Direct");
        analytics.processEvent("/tech/iphone-15", "user_000", "Google");

        analytics.getDashboard();

    }
}
