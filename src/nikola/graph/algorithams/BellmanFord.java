package nikola.graph.algorithams;

import nikola.graph.list_graph.Edge;
import nikola.graph.list_graph.ListGraph;
import nikola.graph.list_graph.Vertex;

public class BellmanFord<E extends Comparable<E>>{

    private void relax(Edge<E> edge){
        Vertex<E> destination = edge.getDestination();
        Vertex<E> source = edge.getSource();

        if (destination.getMinDistance() < Math.min(source.getMinDistance(),edge.getWeight())){
            destination.setMinDistance(Math.min(source.getMinDistance(),edge.getWeight()));
            destination.setPredecessor(source);
        }
    }

    public boolean run(ListGraph<E> graph, Vertex<E> startVertex){
        if (graph == null || startVertex == null)
            return false;

        for (Vertex<E> v : graph.getVertices()){
            v.setMinDistance(Double.MIN_VALUE);
            v.setPredecessor(null);
        }

        startVertex.setMinDistance(Double.MAX_VALUE);

        for (int i = 0; i < graph.getVertices().size(); i++){
            for (Edge<E> edge : graph.getEdges()){
                relax(edge);
            }
        }

        for (Edge<E> edge : graph.getEdges()){
            if (edge.getDestination().getMinDistance() < Math.min(edge.getSource().getMinDistance(),edge.getWeight())){
                return false;
            }
        }

        return true;
    }
}