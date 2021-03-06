import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.Serializable;

public class Page implements Serializable {

    public String url;
    public String name;
    public ArrayList<String> links;
    public HashMap<String, Integer> frequencies;

    public Page(String url, String name, String text, ArrayList<String> links) {
        this.url = url;
        this.name = name;
        this.links = links;

        // Get frequencies
        frequencies = new HashMap<String, Integer>();
        String[] words = text.split("\\W+");
        for (String w : words) {
            Integer n = frequencies.get(w);
            n = (n == null) ? 1 : n++;
            frequencies.put(w, n);
        }
    }

    public double similarity(Page other) {
        HashMap<String, Integer> otherFrequencies = other.getFrequencies();

        double dotProd = 0;
        double magA = 0;
        double magB = 0;

        HashSet<String> intersection = new HashSet<String>(frequencies.keySet());
        intersection.retainAll(otherFrequencies.keySet());

        for (String item : intersection)
            dotProd += frequencies.get(item) * otherFrequencies.get(item);

        for (String k : frequencies.keySet())
            magA += Math.pow(frequencies.get(k), 2);

        for (String k : otherFrequencies.keySet())
            magB += Math.pow(otherFrequencies.get(k), 2);

        return dotProd / Math.sqrt(magA * magB);
    }
    
    public String getName() {
        return this.name;
    }

    public ArrayList<String> getLinks() {
        return this.links;
    }

    public HashMap<String, Integer> getFrequencies() {
        return this.frequencies;
    }
}
