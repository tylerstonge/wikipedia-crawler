import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

// import org.jgrapht.alg.util.UnionFind;

public class Graph {

    HashMap<String, Vertex> vertices;
    HashMap<Integer, Edge> edges;

    public Graph() {
        vertices = new HashMap<>();
        edges = new HashMap<>();
    }

    public LinkedList<Vertex> dijkstra(String s, String d) {
        Vertex src = getVertex(s);
        Vertex dest = getVertex(d);
        Set<Vertex> visited = new HashSet<Vertex>();

        // Initialize hashmaps
        final HashMap<String, Double> dist = new HashMap<String, Double>();
        final HashMap<String, String> prev = new HashMap<String, String>();
        for (String v : vertices.keySet()) {
            dist.put(v, Double.MAX_VALUE);
            prev.put(v, null);
        }
        dist.put(src.name, 0.0);

        // Sort edges by weight
        PriorityQueue<Vertex> pq = new PriorityQueue<Vertex>(vertices.size(), (Vertex v1, Vertex v2) -> {
            double w1 = dist.get(v1.name);
            double w2 = dist.get(v2.name);
            if (w1 < w2) { return -1; }
            if (w1 > w2) { return 1; }
            return 0;
        });

        // Add weights to starting points neighbors
        for (Edge e : src.getEdges()) {
            Vertex other = e.getOther(src);
            prev.put(other.getName(), src.getName());
            dist.put(other.getName(), e.weight);
            pq.add(other);
        }
        visited.add(src);

        // Calculate distances
        while (pq.size() > 0) {
            Vertex next = pq.poll();
            double nextDist = dist.get(next.getName());

            for (Edge e : next.getEdges()) {
                Vertex other = e.getOther(next);
                if (visited.contains(other))
                    continue;

                double currentWeight = dist.get(other.getName());
                double newWeight = nextDist + e.weight;

                if (newWeight < currentWeight) {
                    prev.put(other.getName(), next.getName());
                    dist.put(other.getName(), newWeight);
                    pq.remove(other);
                    pq.add(other);
                }
            }
            visited.add(next);
        }

        // Find path
        LinkedList<Vertex> path = new LinkedList<Vertex>();
        path.add(dest);

        String currentName = dest.name;
        while (!currentName.equals(src.name)) {
            Vertex pred = getVertex(prev.get(currentName));
            currentName = pred.name;
            path.add(0, pred);
        }
        return path;
    }

    public ArrayList<Edge> minSpanningTree() {
        // Prim's MST Algo
        ArrayList<Edge> tree = new ArrayList<Edge>();
        PriorityQueue<Edge> q = new PriorityQueue<Edge>((Object o1, Object o2) -> {
            Edge e1 = (Edge) o1;
            Edge e2 = (Edge) o2;
            if (e1.weight < e2.weight) { return -1; }
            else if (e1.weight > e2.weight) { return 1; }
            return 0;
        });

        for (Map.Entry<Integer, Edge> entry : edges.entrySet()) {
            q.add(entry.getValue());
        }

        Set<Vertex> visited = new HashSet<Vertex>();
        while (!q.isEmpty()) {
            Edge e = q.poll();
            if (visited.contains(e.start) && visited.contains(e.end))
                continue;
            visited.add(e.start);
            for (Edge ee : e.end.edges) {
                if (!visited.contains(ee.start))
                    q.add(ee);
            }
            visited.add(e.end);
            tree.add(e);
        }
        return tree;
    }

    // public ArrayList<Edge> minSpanningTreeKruskal() {
    //     // Kruskal MST Algo
    //     ArrayList<Edge> result = new ArrayList<Edge>();
    //     Collections.sort(edges);
    //
    //     UnionFind<Vertex> unionFind = new UnionFind<Vertex>(new HashSet<Vertex>(vertices));
    //
    //     int numEdges = 0;
    //     for (Edge e : edges) {
    //         try {
    //             if (unionFind.find(e.start) == unionFind.find(e.end))
    //                 continue;
    //             result.add(new Edge(e.start, e.end, e.weight));
    //             unionFind.union(e.start, e.end);
    //             if (++numEdges == vertices.size()) break;
    //         } catch (IllegalArgumentException ex) { }
    //     }
    //
    //     return edges;
    // }

    public Vertex getVertex(String name) {
        return vertices.get(name);
    }

    public void addVertex(Vertex v) {
        vertices.put(v.name, v);
    }

    public HashMap<Integer, Edge> getEdges() {
        return this.edges;
    }

    public boolean addEdge(Vertex start, Vertex end, double weight){
        if (this.vertices.containsKey(start.getName()))
            start = getVertex(start.getName());
        if (this.vertices.containsKey(end.getName()))
            end = getVertex(end.getName());

        if(start.equals(end)) {
            return false;
        }

        Edge e = new Edge(start, end, weight);
        if(edges.containsKey(e.hashCode())) {
            return false;
        }

        //and that the Edge isn't already incident to start of the vertices
        else if(start.containsEdge(e) || end.containsEdge(e)) {
            return false;
        }

        edges.put(e.hashCode(), e);
        start.addEdge(e);
        addVertex(start);
        end.addEdge(e);
        addVertex(end);
        return true;
    }
    
    public Page mostSimilar(String name) {
        Vertex v1 = getVertex(name);
        Page mostSimilar = null;
        double minWeight = Double.MAX_VALUE;
        for (Map.Entry<String, Vertex> entry : vertices.entrySet()) {
            if (!v1.getName().equals(entry.getValue().getName())) {
                if (v1.getPage().similarity(entry.getValue().getPage()) < minWeight) {
                    mostSimilar = entry.getValue().getPage();
                }
            }
        }
        return mostSimilar;
    }

}
