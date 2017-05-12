import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;
import java.util.Map;

public class App {

    public static final Graph g = new Graph();

    public static void main(String[] args) {
        PageReader.getPages("Hacker");
        for (Vertex v : g.dijkstra("Hacker", "Konrad_Zuse")) {
            System.out.print(v.name + " -> ");
        }
        System.out.println("END");
        
        System.out.println(g.mostSimilar("Hacker").getName());
    }
}
