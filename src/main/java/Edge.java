public class Edge implements Comparable<Edge> {
    Vertex start;
    Vertex end;
    double weight;

    public Edge(Vertex start, Vertex end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public double getWeight() {
        return this.weight;
    }

    public String toString() {
        return start.name + " -> " + end.name + " : " + weight;
    }

    public int compareTo(Edge other) {
        if (weight < other.weight)
            return -1;
        else if (weight > other.weight)
            return 1;
        return 0;
    }
    
    public Vertex getStart() {
        return this.start;
    }
    
    public Vertex getEnd() {
        return this.end;
    }

    public Vertex getOther(Vertex v) {
        return (v == start) ? end : start;
    }

    public int hashCode() {
        return (start.name + end.name).hashCode();
    }

    public boolean equals(Object other) {
        if (!(other instanceof Edge))
            return false;

        Edge e = (Edge) other;
        return e.start.equals(this.start) && e.end.equals(this.end);
    }
}
