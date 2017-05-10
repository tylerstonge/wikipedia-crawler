import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class App {
    public static void main(String[] args) {
        try {
            Document d = Jsoup.connect("http://en.wikipedia.com/wiki/Hacker").get();
            Elements body = d.select("#bodyContent p");
            Elements a = d.select("#bodyContent p a[href]");
            String text = Jsoup.parse(body.html()).text();
        
            Pattern linkRE = Pattern.compile("^https://en.wikipedia.org/wiki/*(?:.(?!#cite))*$");
            System.out.println("Links on page");
            for (Element link : a) {
                String href = link.attr("abs:href");
                if (linkRE.matcher(href).matches())
                    System.out.println(href);
            }
            
            Pattern re = Pattern.compile("[^.!?\\s][^.!?]*(?:[.!?](?!['\"]?\\s|$)[^.!?]*)*[.!?]?['\"]?(?=\\s|$)", Pattern.MULTILINE | Pattern.COMMENTS);
            Matcher reMatcher = re.matcher(text);
            System.out.println("Sentences on page");
            while(reMatcher.find()) {
                System.out.println(reMatcher.group());
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
