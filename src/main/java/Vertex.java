import java.util.ArrayList;

public class Vertex {
    public String name;
    public Page page;
    public ArrayList<Edge> edges;
    
    public Vertex(String name, Page page) {
        this.name = name;
        this.page = page;
        this.edges = new ArrayList<Edge>();
        for (String link : page.links) {
            System.out.println("Adding " + link);
            Page p2 = PageReader.getPage(link);
            if (p2 == null) continue;
            edges.add(new Edge(page, p2, page.similarity(p2)));
        }
    }
    
    public ArrayList<Edge> getEdges() {
        return this.edges;
    }
}