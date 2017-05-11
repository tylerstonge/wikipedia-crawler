import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;

public class App {
    public static int PAGE_COUNT = 0;
    public static Queue<String> urls = new LinkedList<String>();

    public static void main(String[] args) {
        ArrayList<Edge> edges = new ArrayList<Edge>();
        urls.add("https://wikipedia.org/wiki/Hackers");

        while (PAGE_COUNT < 500) {
            String url = urls.remove();
            Page p = PageReader.getPage(url);
            ArrayList<Edge> es = PageReader.getEdges(p);
            if (es != null)
                edges.addAll(es);
        }

        for (Edge e : edges) {
            System.out.println(e.toString());
        }
    }
}
