package Graph;
import Element.Edge;
import Element.Vertex;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Kruskal<T> {

    private final int nbrOfVertices;
    private final List<Vertex<T>> vertices;
    private final PriorityQueue<Edge<T>> graph;

    public Kruskal(List<Edge<T>> graph) {
        this.graph = new PriorityQueue<>(graph);
        vertices = getVerticesInGraph(graph);
        nbrOfVertices = vertices.size();
    }
    

    public PriorityQueue<Edge<T>> getGraph() {
        return graph;
    }

    public void MST2() {
        List<Edge<T>> spanningTree = new ArrayList<>();
        
        do {
            Edge<T> edge = graph.poll();
            resetTree(Stream.concat(spanningTree.stream(), Stream.of(edge)).collect(Collectors.toList())); //AB
            if (!new CycleDetectionUndr<T>().hasCycle(vertices)) {
                spanningTree.add(edge);
            }
        } while (spanningTree.size() < nbrOfVertices - 1);
        printTreeInfo(spanningTree);
        
    }

    private List<Vertex<T>> getVerticesInGraph(List<Edge<T>> edges) {
        return (Stream.concat(
                edges.stream().map(Edge::getSource),
                edges.stream().map(Edge::getDestination)
        ).distinct().collect(Collectors.toList()));
    }

    private void resetTree(List<Edge<T>> spanningTree) {
        vertices.forEach(vertex -> {
            vertex.setVisited(false);
            vertex.setNeighbors(new ArrayList<>());
        }); //tách các đỉnh riêng biệt
        
        spanningTree.forEach(edge -> {
            edge.getSource().addNeighbor(edge.getDestination());
            edge.getDestination().addNeighbor(edge.getSource());
        }); //nối các đỉnh lại
    }

    private void printTreeInfo(List<Edge<T>> spanningTree) {
        Integer min = spanningTree.stream()
                .map(Edge::getWeight)
                .reduce(0, Integer::sum);

        spanningTree.forEach(System.out::println);
        System.out.println("Minimum Weight: " + min);
    }
    
    

}