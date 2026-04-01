import java.util.*;

public class problem4 {

    private Map<String, Set<String>> ngramIndex = new HashMap<>();

    private Map<String, Integer> documentSize = new HashMap<>();

    private final int N = 5;

    public void indexDocument(String docId, String content) {
        List<String> ngrams = extractNGrams(content);
        documentSize.put(docId, ngrams.size());

        for (String gram : ngrams) {
            ngramIndex.computeIfAbsent(gram, k -> new HashSet<>()).add(docId);
        }
    }

    public void analyzeDocument(String docId, String content) {
        List<String> inputNGrams = extractNGrams(content);
        int totalGrams = inputNGrams.size();
        System.out.println("analyzeDocument(\"" + docId + "\")");
        System.out.println("→ Extracted " + totalGrams + " n-grams");

        Map<String, Integer> matches = new HashMap<>();

        for (String gram : inputNGrams) {
            if (ngramIndex.containsKey(gram)) {
                for (String matchingDoc : ngramIndex.get(gram)) {
                    matches.put(matchingDoc, matches.getOrDefault(matchingDoc, 0) + 1);
                }
            }
        }

        for (Map.Entry<String, Integer> entry : matches.entrySet()) {
            String otherDocId = entry.getKey();
            int matchCount = entry.getValue();
            double similarity = (matchCount * 100.0) / totalGrams;

            String status = similarity > 50 ? " (PLAGIARISM DETECTED)" :
                    similarity > 10 ? " (suspicious)" : "";

            System.out.printf("→ Found %d matching n-grams with \"%s\"\n", matchCount, otherDocId);
            System.out.printf("→ Similarity: %.1f%%%s\n", similarity, status);
        }
    }

    private List<String> extractNGrams(String text) {
        String[] words = text.toLowerCase().replaceAll("[^a-zA-Z ]", "").split("\\s+");
        List<String> ngrams = new ArrayList<>();

        for (int i = 0; i <= words.length - N; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < N; j++) {
                sb.append(words[i + j]).append(j < N - 1 ? " " : "");
            }
            ngrams.add(sb.toString());
        }
        return ngrams;
    }

    public static void main(String[] args) {
        problem4 detector = new problem4();

        detector.indexDocument("essay_089.txt", "the quick brown fox jumps over the lazy dog often");
        detector.indexDocument("essay_092.txt", "data structures are essential for software engineering and algorithm design");

        String submission = "data structures are essential for software engineering and good logic";
        detector.analyzeDocument("essay_123.txt", submission);
    }
}
