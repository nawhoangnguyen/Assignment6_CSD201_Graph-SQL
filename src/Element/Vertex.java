package Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Vertex<T> implements Comparable<Vertex<T>> {

    private final T data;

    //these field for kruskal algo //
    private boolean visited; //blue mark //completely visited// no otherway to reach to this vertex - // -> already viisited all the paths going throunh this vertex without finding any cycle   
    private boolean beingVisited; //green mark -> on our current path 

    private boolean neighborVisted;
    //

    //these field for dijkstra
    private Integer distance = Integer.MAX_VALUE;
    private List<Vertex<T>> shortestPath = new LinkedList<>();
    private Map<Vertex, Integer> neighborVertexWithWeight = new HashMap<>();

    //
    private List<Vertex<T>> neighbors = new ArrayList<>();

    public Vertex() {
        this.data = null;
    }

    public Vertex(T data) {
        this.data = data;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isBeingVisited() {
        return beingVisited;
    }

    public void setBeingVisited(boolean beingVisited) {
        this.beingVisited = beingVisited;
    }

    public List<Vertex<T>> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(List<Vertex<T>> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Vertex<T> vertex) {
        neighbors.add(vertex);

    }

    public boolean isNeighborVisted() {
        return neighborVisted;
    }

    public void setNeighborVisted(boolean neighborVisted) {
        this.neighborVisted = neighborVisted;
    }

    ///
    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
    ///

    public List<Vertex<T>> getShortestPath() {
        return shortestPath;
    }

    public void setShortestPath(List<Vertex<T>> shortestPath) {
        this.shortestPath = shortestPath;
    }

    public void addNeighborNodeWithWeight(Vertex<T> node, int weight) {
        neighborVertexWithWeight.put(node, weight);
    }

    public Map<Vertex, Integer> getNeighborVertexWithWeight() {
        return neighborVertexWithWeight;
    }

    public void setNeighborVertexWithWeight(Map<Vertex, Integer> neighborVertexWithWeight) {
        this.neighborVertexWithWeight = neighborVertexWithWeight;
    }
    
   

    public T getData() {
        return data;
    }

    @Override
    public int compareTo(Vertex vertex) {

        return (Integer.compare(this.distance, vertex.getDistance()));
    }

    @Override
    public String toString() {
        return "" + data;
    }
}
