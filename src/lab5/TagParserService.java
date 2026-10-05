package lab5;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class TagParserService {
    public static Map<String, Integer> parseAndCountTags(String urlString) throws Exception {
        URI uri = new URI(urlString);
        URL url = uri.toURL();
        Map<String, Integer> tagMap = new HashMap<>();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        // Встановлюємо User-Agent, щоб сайти не блокували запити
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64)");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            // Регулярний вираз для точного пошуку назв відкриваючих і закриваючих HTML-тегів
            Pattern tagPattern = Pattern.compile("<\\s*/?\\s*([a-zA-Z0-9]+)");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = tagPattern.matcher(line);
                while (matcher.find()) {
                    String tagName = matcher.group(1).toLowerCase();
                    tagMap.put(tagName, tagMap.getOrDefault(tagName, 0) + 1);  }
            }
        } finally {  connection.disconnect(); }
        return tagMap;
    }
    public static void printSortedByTagName(Map<String, Integer> tagMap) {
        System.out.println("\n Теги в лексикографічному порядку (зростання)");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(tagMap.entrySet());
        list.sort(Map.Entry.comparingByKey());
        for (Map.Entry<String, Integer> entry : list) {
            System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue()); }
    }
    public static void printSortedByFrequency(Map<String, Integer> tagMap) {
        System.out.println("\n- Теги за частотою появи (зростання) -");
        List<Map.Entry<String, Integer>> list = new ArrayList<>(tagMap.entrySet());
        list.sort(Map.Entry.comparingByValue());
        for (Map.Entry<String, Integer> entry : list) {
            System.out.printf("%-15s : %d%n", entry.getKey(), entry.getValue()); }
    }
}