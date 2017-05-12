import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
    
    ArrayList<Vertex> vertices;
    ArrayList<Edge> edges;
    
    public Graph(HashMap<String, Page> pages) {
        vertices = new ArrayList<Vertex>();
        edges = new ArrayList<Edge>();
        for (Map.Entry<String, Page> entry : pages.entrySet()) {
            Vertex v = new Vertex(entry.getKey(), entry.getValue());
            vertices.add(v);
            for (Edge e : v.getEdges()) {
                edges.add(e);
            }
        }
        System.out.println(vertices.size() + ":" + edges.size());
    }
    
    public void minSpanningTree() {
        Set<Vertex> visited = new HashSet<Vertex>();
    }
    
}