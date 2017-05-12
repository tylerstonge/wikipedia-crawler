import java.util.ArrayList;

public class Vertex {
    public String name;
    public Page page;
    public ArrayList<Edge> edges;

    public Vertex(String name, Page page) {
        this.name = name;
        this.page = page;
        this.edges = new ArrayList<Edge>();
    }

    public void addEdge(Edge e) {
        if (edges.contains(e))
            return;
        edges.add(e);
    }

    public boolean containsEdge(Edge e) {
        return edges.contains(e);
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<Edge> getEdges() {
        return this.edges;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Vertex))
            return false;

        Vertex v = (Vertex) other;
        return this.name.equals(v.name);
    }
}
