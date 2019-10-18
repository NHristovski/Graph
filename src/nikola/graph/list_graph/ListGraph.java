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

        Vertex<E> destination = vertices.get(indexes.get(end));

        runDijkstra(start);

        return destination.getMinDistance();
    }

    private void runDijkstra(E start) {
        Vertex<E> source = vertices.get(indexes.get(start));

        Dijkstra<E> dijkstra = new Dijkstra<>();

        dijkstra.run(this, source);
    }

    public List<Vertex<E>> getMinPathDijkstra(E start,E end){
        List<Vertex<E>> path = new ArrayList<>();

        runDijkstra(start);

        Vertex<E> currentVertex = vertices.get(indexes.get(end));

        while (currentVertex.getPredecessor() != null){
            path.add(currentVertex);

            currentVertex = currentVertex.getPredecessor();
        }

        return path;
    }

    public double getMinDistanceBellmanFord(E start,E end){
        Vertex<E> source = this.getVertex(start);
        Vertex<E> destination = this.getVertex(end);

        BellmanFord<E> bellmanFord = new BellmanFord<>();

        if (bellmanFord.run(this, source))
            return destination.getMinDistance();
        else{
            System.err.println("Bellman Ford Failed! Cannot return min distance!");
            return BELLMAN_FORD_ERROR_CODE;
        }
    }

    public List<Vertex<E>> getMinPathBellmanFord(E start,E end){
        Vertex<E> source = this.getVertex(start);

        BellmanFord<E> bellmanFord = new BellmanFord<>();

        if (bellmanFord.run(this, source)) {

            List<Vertex<E>> path = new ArrayList<>();

            Vertex<E> currentVertex = this.getVertex(end);

            while (currentVertex.getPredecessor() != null){
                path.add(currentVertex);

                currentVertex = currentVertex.getPredecessor();
            }

            return path;
        }
        else{
            System.err.println("Bellman Ford Failed! Cannot return min path!");
            return Collections.emptyList();
        }
    }

}