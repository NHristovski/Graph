package nikola.graph.algorithams;


import nikola.graph.list_graph.Edge;
import nikola.graph.list_graph.ListGraph;
import nikola.graph.list_graph.Vertex;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra<E extends Comparable<E>>{

    public Dijkstra(){
    }

    private Hashtable<Vertex<E>, List<Edge<E>>> createEdgesHashtable(ListGraph<E> graph){
        Hashtable<Vertex<E>, List<Edge<E> > > table = new Hashtable<>();

        for (Vertex<E> v : graph.getVertices()){
            table.put(v,new LinkedList<>());
        }

        for (Edge<E> e : graph.getEdges()){
            table.get(e.getSource()).add(e);
        }

        return table;
    }

    public void run(ListGraph<E> graph, Vertex<E> startVertex){

        if (graph == null || startVertex == null) {
            System.err.println("Either graph or startVertex is null!");
            System.err.println("graph: " + graph);
            System.err.println("startVertex: " + startVertex);
            return;
        }

        for (Vertex<E> v : graph.getVertices()){
            v.setMinDistance(Double.MAX_VALUE);
            v.setPredecessor(null);
        }

        startVertex.setMinDistance(0d);

        PriorityQueue<Vertex<E>> queue = new PriorityQueue<>(graph.getVertices());

        Hashtable<Vertex<E>, List<Edge<E>>> edges = createEdgesHashtable(graph);

        while (!queue.isEmpty()){
            Vertex<E> currentVertex = queue.poll();

            List<Edge<E>> currentEdges = edges.get(currentVertex);

            for (Edge<E> edge : currentEdges){

                Vertex<E> destinationVertex = edge.getDestination();

                double newDistance = currentVertex.getMinDistance() + edge.getWeight();

                if (destinationVertex.getMinDistance() > newDistance){

                    destinationVertex.setMinDistance(newDistance);
                    destinationVertex.setPredecessor(currentVertex);
                    queue.add(destinationVertex);
                }
            }
        }
    }
}

