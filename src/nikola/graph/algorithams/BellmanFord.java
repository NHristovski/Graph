package nikola.graph.algorithams;

import nikola.graph.list_graph.Edge;
import nikola.graph.list_graph.ListGraph;
import nikola.graph.list_graph.Vertex;

public class BellmanFord<E extends Comparable<E>>{

    private void relax(Edge<E> edge){
        Vertex<E> destination = edge.getDestination();
        Vertex<E> source = edge.getSource();
        double weight = edge.getWeight();

        if (destination.getMinDistance() > source.getMinDistance() + weight){
            destination.setMinDistance(source.getMinDistance() + weight);
            destination.setPredecessor(source);
        }
    }

    public boolean run(ListGraph<E> graph, Vertex<E> startVertex){
        if (graph == null || startVertex == null) {
            System.err.println("Either graph or startVertex is null!");
            System.err.println("graph: " + graph);
            System.err.println("startVertex: " + startVertex);

            return false;
        }

        for (Vertex<E> v : graph.getVertices()){
            v.setMinDistance(Double.MAX_VALUE);
            v.setPredecessor(null);
        }

        startVertex.setMinDistance(0d);

        for (int i = 0; i < graph.getVertices().size(); i++){
            for (Edge<E> edge : graph.getEdges()){
                relax(edge);
            }
        }

        for (Edge<E> edge : graph.getEdges()){
            if (edge.getDestination().getMinDistance() > edge.getSource().getMinDistance() + edge.getWeight()){
                return false;
            }
        }

        return true;
    }
}