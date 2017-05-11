import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Collection;

public class PageReader {

    public static final int NUMBER_TO_READ = 10;

    public static Page getPage(String url) {
        try {
            Document d = Jsoup.connect(url).get();
            Elements body = d.select("#bodyContent p");
            Elements a = d.select("#bodyContent p a[href]");
            String text = Jsoup.parse(body.html()).text();

            Pattern linkRE = Pattern.compile("^https://en.wikipedia.org/wiki/*(?:.(?!#cite))*$");
            ArrayList<String> links = new ArrayList<String>();
            int count = 0;
            for (Element link : a) {
                String href = link.attr("abs:href");
                if (linkRE.matcher(href).matches()) {
                    links.add(href);
                    if (++count > NUMBER_TO_READ)
                        break;
                }
            }
            return new Page(url, text, links);
        }  catch(HttpStatusException e) {
            // Carry on
        } catch(IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<Edge> getEdges(Page startPage) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        for (String link : startPage.getLinks()) {
            Page other = getPage(link);
            if (other != null) {
                App.PAGE_COUNT++;
                App.urls.add(link);
                Edge e = new Edge(startPage, other, 1 - startPage.similarity(other));
                edges.add(e);
            }
        }
        return edges;
    }

}
