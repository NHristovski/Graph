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

    private Hashtable<Vertex<E>, List<Edge<E>>> makeHashtable(ListGraph<E> graph){
        Hashtable<Vertex<E>, List<Edge<E> > > table = new Hashtable<>();
        List<Edge<E>> lst;
        for (Vertex<E> v : graph.getVertices()){
            lst = new LinkedList<>();
            table.put(v,lst);
        }

        for (Edge<E> e : graph.getEdges()){
            table.get(e.getSource()).add(e);
        }

        return table;
    }

    public void run(ListGraph<E> graph, Vertex<E> startVertex){

        if (graph == null || startVertex == null)
            return;

        for (Vertex<E> v : graph.getVertices()){
            v.setMinDistance(Double.MIN_VALUE);
            v.setPredecessor(null);
        }

        double result = Double.MAX_VALUE;

        for (Edge<E> edge : graph.getEdges()){
            if (edge.equals(new Edge<E>(startVertex,startVertex,0))){
                result = edge.getWeight();
            }
        }
        startVertex.setMinDistance(result);
        PriorityQueue<Vertex<E>> queue = new PriorityQueue<>(graph.getVertices());
        Hashtable<Vertex<E>, List<Edge<E>>> table = makeHashtable(graph);

        while (!queue.isEmpty()){
            Vertex<E> u = queue.poll();

            for (Edge<E> e : table.get(u)){
                Vertex<E> v = e.getDestination();
                if (v.getMinDistance() < Math.min(u.getMinDistance(),e.getWeight())){
                    v.setMinDistance(Math.min(u.getMinDistance(),e.getWeight()));
                    v.setPredecessor(u);
                    queue.add(v);
                }
            }
        }

    }

}

