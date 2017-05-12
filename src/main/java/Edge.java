public class Edge {
    Page start;
    Page end;
    double weight;

    public Edge(Page start, Page end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public String toString() {
        return start.name + " -> " + end.name + " : " + weight;
    }
}
