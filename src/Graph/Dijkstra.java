/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import Element.Vertex;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.layout.Priority;

/**
 *
 * @author Admin
 */
public class Dijkstra<T> {
    private List<Vertex<T>> listVertices;

    public Dijkstra(List<Vertex<T>> listVertices) {
        this.listVertices = listVertices;
    }
    
    
     public void Findpath(T vertex, T endVertex) {
         Vertex<T> source = new Vertex<>(vertex);
         Vertex<T> destination = new Vertex<>(endVertex);
         
         
         for (Vertex<T> vertex1 : listVertices) {
             if(vertex1.getData().equals(source.getData())) {
                 source = vertex1;
                 break;
             }
         }
         
         for (Vertex<T> vertex2 : listVertices) {
             if(vertex2.getData().equals(destination.getData())) {
                 destination = vertex2;
                 break;
             }
             
         }
        System.out.println("Min path from " + vertex + " -> " + endVertex + " using Dijkstra Algorithm");
         
        source.setDistance(0); //set the first elemet with value = 0
        
        Set<Vertex<T>> settledNodes = new HashSet<>();//settledNodes
        
        Queue<Vertex<T>> unsettledNodes = new PriorityQueue<>(Collections.singleton(source));
        
        while(!unsettledNodes.isEmpty()) {
            Vertex<T> currentNode = unsettledNodes.poll(); 
            currentNode.getNeighborVertexWithWeight()
                       .entrySet().stream()
                    .filter(entry -> !settledNodes.contains(entry.getKey()))
                    .forEach(entry -> {
                        evaluateDistanceAndPath(entry.getKey(), entry.getValue(), currentNode);
                        unsettledNodes.add(entry.getKey());
                    });
             settledNodes.add(currentNode);
        }      
        
         printPaths(listVertices, destination);
        
           
        
    }
    
    private void evaluateDistanceAndPath(Vertex<T> adjacentNode, Integer edgeWeight, Vertex<T> sourceNode) {
        Integer newDistance = sourceNode.getDistance() + edgeWeight;
        if (newDistance < adjacentNode.getDistance()) {
            adjacentNode.setDistance(newDistance);
            adjacentNode.setShortestPath(Stream.concat(sourceNode.getShortestPath().stream(), Stream.of(sourceNode)).collect(Collectors.toList()));
        }
    }
    
    private void printPaths(List<Vertex<T>> nodes, Vertex<T> destination) {
        nodes.forEach(node -> {
            String path = node.getShortestPath().stream()
                    .map(Vertex::getData).map(Objects::toString)
                    .collect(Collectors.joining(" -> "));

             if (destination.getData().equals(node.getData())) {
                    System.out.printf("%s -> %s : %s\n",path, node.getData(), node.getDistance() );
             }                
             
            
        });
    }
}
