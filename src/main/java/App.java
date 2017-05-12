import java.util.Queue;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        HashMap<String, Page> pages = PageReader.getPages("Hacker");
        new Graph(pages);
    }
}
