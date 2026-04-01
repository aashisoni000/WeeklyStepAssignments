import java.util.*;

public class problem10 {
    static class Video {
        String id;
        String data;
        Video(String id, String data) { this.id = id; this.data = data; }
    }

    private final int L1_SIZE = 10;
    private final int L2_SIZE = 20;

    private Map<String, Video> l1 = Collections.synchronizedMap(
            new LinkedHashMap<String, Video>(L1_SIZE, 0.75f, true) {
                protected boolean removeEldestEntry(Map.Entry<String, Video> e) { return size() > L1_SIZE; }
            });

    private Map<String, String> l2 = new HashMap<>();
    private Map<String, Integer> accessCount = new HashMap<>();

    private double l1Hits, l2Hits, l3Hits;

    public String getVideo(String id) {
        if (l1.containsKey(id)) {
            l1Hits++;
            return "L1 HIT: " + l1.get(id).data;
        }

        if (l2.containsKey(id)) {
            l2Hits++;
            Video v = new Video(id, "SSD_DATA_" + id);
            accessCount.put(id, accessCount.getOrDefault(id, 0) + 1);
            if (accessCount.get(id) > 2) {
                l1.put(id, v);
            }
            return "L2 HIT: " + v.data;
        }

        l3Hits++;
        Video v = new Video(id, "DB_DATA_" + id);
        l2.put(id, "SSD_PATH_" + id);
        accessCount.put(id, 1);
        return "L3 HIT: " + v.data;
    }

    public void printStats() {
        double total = l1Hits + l2Hits + l3Hits;
        System.out.printf("L1 Hit Rate: %.1f%%, L2 Hit Rate: %.1f%%, L3 Hit Rate: %.1f%%\n",
                (l1Hits/total)*100, (l2Hits/total)*100, (l3Hits/total)*100);
    }

    public static void main(String[] args) {
        problem10 netflix = new problem10();
        System.out.println(netflix.getVideo("v1"));
        System.out.println(netflix.getVideo("v1"));
        System.out.println(netflix.getVideo("v1"));
        System.out.println(netflix.getVideo("v1"));
        netflix.printStats();
    }
}
