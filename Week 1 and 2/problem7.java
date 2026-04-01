import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class problem7 {
    class TrieNode {
        Map<Character, TrieNode> children = new HashMap<>();
        List<String> topSuggestions = new ArrayList<>();
    }

    private final TrieNode root = new TrieNode();
    private final Map<String, Integer> freqs = new ConcurrentHashMap<>();

    public void updateFrequency(String query) {
        freqs.merge(query, 1, Integer::sum);
        TrieNode curr = root;
        for (char c : query.toCharArray()) {
            curr.children.putIfAbsent(c, new TrieNode());
            curr = curr.children.get(c);
            if (!curr.topSuggestions.contains(query)) curr.topSuggestions.add(query);
            curr.topSuggestions.sort((a, b) -> freqs.get(b) - freqs.get(a));
            if (curr.topSuggestions.size() > 10) curr.topSuggestions.remove(10);
        }
    }

    public List<String> search(String prefix) {
        TrieNode curr = root;
        for (char c : prefix.toCharArray()) {
            if (!curr.children.containsKey(c)) return Collections.emptyList();
            curr = curr.children.get(c);
        }
        return curr.topSuggestions;
    }

    public static void main(String[] args) {
        problem7 p = new problem7();
        p.updateFrequency("java");
        System.out.println(p.search("ja"));
    }
}
