import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class problem3 {

    class DNSEntry {
        String ip;
        long expiryTime;

        DNSEntry(String ip, int ttlSeconds) {
            this.ip = ip;
            this.expiryTime = System.currentTimeMillis() + (ttlSeconds * 1000L);
        }

        boolean isExpired() {
            return System.currentTimeMillis() > expiryTime;
        }
    }

    private final int MAX_CAPACITY = 1000;
    private final Map<String, DNSEntry> cache = Collections.synchronizedMap(
            new LinkedHashMap<String, DNSEntry>(MAX_CAPACITY, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, DNSEntry> eldest) {
                    return size() > MAX_CAPACITY;
                }
            }
    );

    private double hits = 0;
    private double misses = 0;

    public String resolve(String domain) {
        long startTime = System.nanoTime();
        DNSEntry entry = cache.get(domain);

        if (entry != null && !entry.isExpired()) {
            hits++;
            long duration = System.nanoTime() - startTime;
            return "Cache HIT → " + entry.ip + " (retrieved in " + (duration / 1_000_000.0) + "ms)";
        }

        misses++;
        String status = (entry == null) ? "MISS" : "EXPIRED";

        String upstreamIp = queryUpstreamDNS(domain);
        int ttl = 300;

        cache.put(domain, new DNSEntry(upstreamIp, ttl));
        return "Cache " + status + " → Query upstream → " + upstreamIp + " (TTL: " + ttl + "s)";
    }

    private String queryUpstreamDNS(String domain) {
        return "172.217.14." + (new Random().nextInt(255));
    }

    public String getCacheStats() {
        double total = hits + misses;
        double hitRate = (total == 0) ? 0 : (hits / total) * 100;
        return String.format("getCacheStats() → Hit Rate: %.1f%%, Hits: %.0f, Misses: %.0f", hitRate, hits, misses);
    }

    public static void main(String[] args) throws InterruptedException {
        problem3 dns = new problem3();

        System.out.println(dns.resolve("google.com"));

        System.out.println(dns.resolve("google.com"));

        System.out.println(dns.getCacheStats());

    }
}
