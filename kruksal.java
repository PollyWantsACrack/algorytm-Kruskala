import java.util.*;

// Klasa reprezentująca krawędź grafu
class GraphEdge implements Comparable<GraphEdge> {
    int source, destination, weight;

    public GraphEdge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    @Override
    public int compareTo(GraphEdge other) {
        return this.weight - other.weight;
    }
}

// Klasa reprezentująca graf
class Graph {
    private int V; // Liczba wierzchołków
    private List<GraphEdge> edges; // Lista krawędzi

    public Graph(int V) {
        this.V = V;
        edges = new ArrayList<>();
    }

    // Dodawanie krawędzi do grafu
    public void addEdge(int source, int destination, int weight) {
        edges.add(new GraphEdge(source, destination, weight));
    }

    // Metoda Kruskala znajdująca minimalne drzewo rozpinające
    public List<GraphEdge> kruskalMST() {
        List<GraphEdge> mst = new ArrayList<>();
        Collections.sort(edges);

        DisjointSet disjointSet = new DisjointSet(V);
        for (GraphEdge edge : edges) {
            int root1 = disjointSet.find(edge.source);
            int root2 = disjointSet.find(edge.destination);
            if (root1 != root2) {
                mst.add(edge);
                disjointSet.union(root1, root2);
            }
        }
        return mst;
    }

    // Metoda Prima znajdująca minimalne drzewo rozpinające
    public List<GraphEdge> primMST() {
        List<GraphEdge> mst = new ArrayList<>();
        boolean[] visited = new boolean[V];
        PriorityQueue<GraphEdge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.weight));

        // Rozpoczynamy od pierwszego wierzchołka
        visited[0] = true;
        for (GraphEdge edge : edges) {
            if (edge.source == 0) {
                pq.add(edge);
            }
        }

        while (!pq.isEmpty()) {
            GraphEdge minEdge = pq.poll();
            if (!visited[minEdge.destination]) {
                mst.add(minEdge);
                visited[minEdge.destination] = true;
                for (GraphEdge edge : edges) {
                    if (edge.source == minEdge.destination && !visited[edge.destination]) {
                        pq.add(edge);
                    }
                }
            }
        }

        return mst;
    }
}

// Klasa reprezentująca strukturę zbiorów rozłącznych
class DisjointSet {
    private int[] parent;
    private int[] rank;

    public DisjointSet(int n) {
        parent = new int[n];
        rank = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
        }
    }

    public int find(int x) {
        if (parent[x] != x) {
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);

        if (rootX != rootY) {
            if (rank[rootX] < rank[rootY]) {
                parent[rootX] = rootY;
            } else if (rank[rootX] > rank[rootY]) {
                parent[rootY] = rootX;
            } else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
        }
    }
}
