package Graph;

import Element.Edge;
import Element.Vertex;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Euler<T> {

    private List<Edge<T>> listOfEdgesDouble;
    private List<Vertex<T>> listOfVertex;
   

    public Euler(List<Edge<T>> listOfEdgesDouble, List<Vertex<T>> listOfVertex) {
        this.listOfEdgesDouble = listOfEdgesDouble;
        this.listOfVertex = listOfVertex;
    
    }

    public void EulerCycle(T name) {
        if (hasEulerCircuit()) {
            System.out.println("");
            System.out.println("Found Euler circuit!!");
        
        
        Vertex<T> startVertex = new Vertex<>(name);
        for (Vertex<T> vertex : listOfVertex) {
            if (vertex.getData().equals(name)) {
                startVertex = vertex;
            }
        }

        int countVisited = (listOfEdgesDouble.size() / 2 );

        List<Edge<T>> listOfEulerPath = new ArrayList<>();
        for (Vertex<T> vertex : listOfVertex) {
            vertex.setVisited(false);
        }
        for (Vertex<T> vertex : listOfVertex) {
            vertex.setVisited(false);
        }
        for (Edge<T> edge : listOfEdgesDouble) {
            edge.setIncluded(false);
        }

        Vertex<T> nextVertex = startVertex;

        while (countVisited > 0) {

            Edge<T> nextEdge = new Edge(listOfVertex.get(1), listOfVertex.get(0), 123);

            for (Edge<T> ledge : listOfEdgesDouble) {

                if (!ledge.isIncluded() && ledge.getSource().getData().equals(nextVertex.getData())) {

                    nextEdge = ledge;

                }

            }
            nextVertex = nextEdge.getDestination();
            listOfEulerPath.add(nextEdge);

            nextEdge.setIncluded(true);

            for (Edge<T> edgeTraverse : listOfEdgesDouble) {
                if (edgeTraverse.getSource().getData().equals(nextEdge.getDestination().getData())
                        && edgeTraverse.getDestination().getData().equals(nextEdge.getSource().getData())) {
                    edgeTraverse.setIncluded(true);
                }

            }
            countVisited--;

        }

        for (Edge<T> edge : listOfEulerPath) {
            System.out.println(" " + edge.getSource().getData() + " -> " + edge.getDestination().getData());

        }
        } else {
            System.out.println("There is no Euler Circuit!!");
        }
        
        System.out.println("");
    }
    
    private boolean hasEulerCircuit() {
        for (Vertex<T> vertex : listOfVertex) {
            int degree = 0;
            for (Edge<T> edge : listOfEdgesDouble) {
                if (edge.getSource().equals(vertex) || edge.getDestination().equals(vertex)) {
                    degree++;
                }
            }
            if (degree % 2 != 0) {
                return false;
            }
        }
        return true;
    }

}
