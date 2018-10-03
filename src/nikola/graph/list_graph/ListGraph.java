package nikola.graph.list_graph;

import nikola.graph.matrix_graph.MatrixGraph;
import nikola.graph.algorithams.BellmanFord;
import nikola.graph.algorithams.Dijkstra;

import java.util.*;

public class ListGraph<E extends Comparable<E>> {

    public static final double BELLMAN_FORD_ERROR_CODE = -1d;

    private List<Vertex<E>> vertices;
    private List<Edge<E>> edges;
    private Map<E,Integer> indexes; // E(data of the vertex) -> Integer (position in list vertices)


    // *************************************************************************
    //                              CONSTRUCTORS
    //**************************************************************************

    /**
     * Use when the number of vertices and edges is unknown
     */
    public ListGraph(){
        vertices = new ArrayList<>();
        edges = new ArrayList<>();
        indexes = new HashMap<>();
    }

    /**
     * Use when the number of  edges is unknown
     */
    public ListGraph(int numberOfVertices){
        initializeVertices(numberOfVertices);
        edges = new ArrayList<>();
    }

    /**
     * Use when the number of vertices and edges is known, for better performance
     */
    public ListGraph(int numberOfVertices,int numberOfEdges){
        initializeVertices(numberOfVertices);
        edges = new ArrayList<>(numberOfEdges);
    }


    private void initializeVertices(int numberOfVertices) {
        vertices = new ArrayList<>(numberOfVertices);
        indexes = new HashMap<>(numberOfVertices);
    }

    // *************************************************************************
    //                              METHODS
    //**************************************************************************

    public void addVertex(E data){
        Vertex<E> vertex = new Vertex<>(data);
        vertices.add(vertex);

        int index = vertices.size() - 1;
        indexes.put(data, index);
    }

    public Vertex<E> getVertex(E data){
        return vertices.get(indexes.get(data));
    }

    private boolean addEdge(Vertex<E> source,Vertex<E> destination,double weight){
        return edges.add(new Edge<>(source,destination,weight));
    }

    public boolean addDirectedEdge(E source,E destination,double weight){
        Vertex<E> s = vertices.get(indexes.get(source));
        Vertex<E> d = vertices.get(indexes.get(destination));

        return addEdge(s,d,weight);
    }

    public boolean addUndirectedEdge(E source,E destination,double weight){
        Vertex<E> s = vertices.get(indexes.get(source));
        Vertex<E> d = vertices.get(indexes.get(destination));

        return addEdge(s,d,weight) && addEdge(d,s,weight);
    }

    public List<Vertex<E>> getVertices(){
        return Collections.unmodifiableList(this.vertices);
    }

    public List<Edge<E>> getEdges(){
        return Collections.unmodifiableList(this.edges);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Graph { \nvertices: [\n");
        for (Vertex<E> v : this.getVertices()){
            sb.append(" " + v.toString() + ",\n");
        }
        sb.append("] }");

        return sb.toString().replaceFirst(",\n]","\n]");
    }




    public MatrixGraph convertToMatrixGraph(){
        int numOfVertices = vertices.size();
        double[][] graph = new double[numOfVertices][numOfVertices];

        for (Edge<E> edge : edges){
            int source = indexes.get(edge.getSource().getData());
            int destination = indexes.get(edge.getDestination().getData());
            graph[source][destination] = edge.getWeight();
        }

        return new MatrixGraph(graph);
    }

    public double getMinDistanceDijkstra(E start,E end){
        Vertex<E> source = vertices.get(indexes.get(start));
        Vertex<E> destination = vertices.get(indexes.get(end));
        Dijkstra<E> dijkstra = new Dijkstra<>();

        dijkstra.run(this, source);

        return destination.getMinDistance();
    }

    public double getMinDistanceBellmanFord(E start,E end){
        Vertex<E> source = vertices.get(indexes.get(start));
        Vertex<E> destination = vertices.get(indexes.get(end));

        BellmanFord<E> bellmanFord = new BellmanFord<>();

        if (bellmanFord.run(this, source))
            return destination.getMinDistance();
        else{
            return BELLMAN_FORD_ERROR_CODE;
        }
    }

}