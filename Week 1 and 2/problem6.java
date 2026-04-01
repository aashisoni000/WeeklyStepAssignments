import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class problem6 {

    static class TokenBucket {
        private final long maxTokens;
        private final long refillRatePerMillis;
        private double currentTokens;
        private long lastRefillTimestamp;

        public TokenBucket(long limitPerHour) {
            this.maxTokens = limitPerHour;
            this.currentTokens = limitPerHour;

            this.refillRatePerMillis = limitPerHour;
            this.lastRefillTimestamp = System.currentTimeMillis();
        }

        public synchronized boolean tryConsume() {
            refill();
            if (currentTokens >= 1.0) {
                currentTokens -= 1.0;
                return true;
            }
            return false;
        }

        private void refill() {
            long now = System.currentTimeMillis();
            long timeElapsed = now - lastRefillTimestamp;

            double tokensToAdd = (double) (timeElapsed * refillRatePerMillis) / TimeUnit.HOURS.toMillis(1);

            if (tokensToAdd > 0) {
                currentTokens = Math.min(maxTokens, currentTokens + tokensToAdd);
                lastRefillTimestamp = now;
            }
        }

        public long getRemaining() {
            refill();
            return (long) Math.floor(currentTokens);
        }

        public long getSecondsUntilReset() {
            long nextFullRefill = lastRefillTimestamp + TimeUnit.HOURS.toMillis(1);
            return Math.max(0, (nextFullRefill - System.currentTimeMillis()) / 1000);
        }
    }

    private final Map<String, TokenBucket> clientLimits = new ConcurrentHashMap<>();
    private final long LIMIT = 1000;

    public String checkRateLimit(String clientId) {
        TokenBucket bucket = clientLimits.computeIfAbsent(clientId, k -> new TokenBucket(LIMIT));

        if (bucket.tryConsume()) {
            return "Allowed (" + bucket.getRemaining() + " requests remaining)";
        } else {
            return "Denied (0 requests remaining, retry after " + bucket.getSecondsUntilReset() + "s)";
        }
    }

    public static void main(String[] args) {
        problem6 gateway = new problem6();
        String client = "abc123";

        System.out.println("Initial Checks:");
        System.out.println("checkRateLimit(\"" + client + "\") → " + gateway.checkRateLimit(client));
        System.out.println("checkRateLimit(\"" + client + "\") → " + gateway.checkRateLimit(client));

        gateway.clientLimits.get(client).currentTokens = 0;
        System.out.println("\nAfter Limit Exceeded:");
        System.out.println("checkRateLimit(\"" + client + "\") → " + gateway.checkRateLimit(client));
    }
}
