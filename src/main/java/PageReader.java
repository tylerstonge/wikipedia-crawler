import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import org.jsoup.HttpStatusException;

import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Queue;
import java.util.LinkedList;

public class PageReader {
    public static final int NUMBER_TO_READ = 50;

    public static Page getPage(String url) {
        Page p = null;
        String name = url.substring(url.lastIndexOf('/') + 1);
        File f = new File("cache/" + name);
        if (f.exists()) {
            // System.out.println("Reading from cache");
            p = read(f);
        } else {
            // No file
            try {
                // Fetch
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
                p = new Page(url, name, text, links);
                write(p);
            }  catch(HttpStatusException e) {
                // Carry on
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
        return p;
    }

    public static void getPages(String s) {
        Queue<String> urls = new LinkedList<String>();
        urls.add("https://wikipedia.org/wiki/" + s);

        int count = 0;
        while (count < 600) {
            Page page = getPage(urls.remove());
            Vertex v1 = new Vertex(page.name, page);
            App.g.addVertex(v1);
            for (String link : page.getLinks()) {
                urls.add(link);
                Page l = getPage(link);
                if (l == null) continue;
                Vertex v2 = new Vertex(l.name, l);
                App.g.addVertex(v2);
                App.g.addEdge(v1, v2, 1 - page.similarity(l));
                count++;
            }
        }
    }

    public static Page read(File f) {
        Page p = null;
        try {
            FileInputStream fin = new FileInputStream(f);
            ObjectInputStream ois = new ObjectInputStream(fin);
            p = (Page) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

    public static void write(Page p) {
        FileOutputStream fout = null;
        ObjectOutputStream oos = null;

        try {
            File f = new File("cache/" + p.name);
            f.createNewFile();

            fout = new FileOutputStream(f, false);
            oos = new ObjectOutputStream(fout);
            oos.writeObject(p);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fout != null) {
            try {
                    fout.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
