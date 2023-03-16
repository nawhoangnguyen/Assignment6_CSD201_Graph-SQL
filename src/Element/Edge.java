
package Element;


public class Edge<T> implements Comparable<Edge<T>> {

    private final Vertex<T> source;
    private final Vertex<T> destination;
    private final int weight;
        private boolean included;


    public Edge(Vertex<T> source, Vertex<T> destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
       source.addNeighbor(destination);
//        destination.addNeighbor(source);
source.addNeighborNodeWithWeight(destination, weight);
//        destination.addNeighborNodeWithWeight(source, weight);
    }

    public Edge(Vertex<T> source, Vertex<T> destination) {
        this.source = source;
        this.destination = destination;
        source.addNeighbor(destination);
//        destination.addNeighbor(source);
        weight = 0;
    }

    public Vertex<T> getSource() {
        return source;
    }

    public Vertex<T> getDestination() {
        return destination;
    }

    public int getWeight() {
        return weight;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    @Override
    public String toString() {
        return "" + source + destination + " (weight: " + weight + ")";
    }

    @Override
    public int compareTo(Edge<T> edge) {

        if (Integer.compare(weight, edge.getWeight()) < 0 && Integer.compare(this.source.getData().toString().charAt(0), edge.getSource().getData().toString().charAt(0)) < 0) {
            return -1;
       
        } else if (Integer.compare(weight, edge.getWeight()) == 0 && Integer.compare(this.source.getData().toString().charAt(0), edge.getSource().getData().toString().charAt(0)) > 0) {
            return 1;
            
        } else if (Integer.compare(weight, edge.getWeight()) == 0 && Integer.compare(this.source.getData().toString().charAt(0), edge.getSource().getData().toString().charAt(0)) == 0) {
            return 0;
            
        } else if (Integer.compare(weight, edge.getWeight()) > 0 && Integer.compare(this.source.getData().toString().charAt(0), edge.getSource().getData().toString().charAt(0)) > 0) {
            return 1;
        }
        return Integer.compare(weight, edge.getWeight());
    }

}
