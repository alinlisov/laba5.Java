package lab5;
import java.io.Serializable;
import java.util.Map;
public class TagCounter implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String url;
    private final Map<String, Integer> tagFrequencies;
    public TagCounter(String url, Map<String, Integer> tagFrequencies) {
        this.url = url;
        this.tagFrequencies = tagFrequencies; }
    public String getUrl() { return url; }
    public Map<String, Integer> getTagFrequencies() { return tagFrequencies; }
    @Override
    public String toString() {
        return "TagCounter{URL='" + url + "', Унікальних тегів=" + tagFrequencies.size() + '}'; }
}