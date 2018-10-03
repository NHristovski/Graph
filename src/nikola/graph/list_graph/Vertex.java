package nikola.graph.list_graph;

import java.util.Objects;

public class Vertex<E extends Comparable<E>> implements Comparable<Vertex<E>>{
    private E data;
    private double minDistance;
    private Vertex<E> predecessor;

    /**
     * Creates new Vertex with minDistance set to Double.MaxValue and predecessor to null
     * 
     * @param data Value in the vertex
     *            
     */
    public Vertex(E data){
        this.data = data;
        this.minDistance = Double.MAX_VALUE;
        this.predecessor = null;
    }

    public boolean hasPredecessor(){
        return this.predecessor != null;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }

    public Vertex<E> getPredecessor() {
        return predecessor;
    }

    public void setPredecessor(Vertex<E> predecessor) {
        this.predecessor = predecessor;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    /**
     * 
     * @param other otherVertex
     * @return comparision based on min distance
     */
    @Override
    public int compareTo(Vertex<E> other) {
        return Double.compare(minDistance, other.getMinDistance());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex<?> vertex = (Vertex<?>) o;
        return Double.compare(vertex.minDistance, minDistance) == 0 &&
                Objects.equals(data, vertex.data);
    }

    @Override
    public String toString(){
        return new StringBuilder()
                .append("Vertex: { data: ")
                .append(this.data)
                .append(", minDistance: ")
                .append(this.minDistance)
                .append(", predecessor: ")
                .append(getPredecessorData())
                .append(" }")
                .toString();
    }

    private String getPredecessorData() {
        if (this.hasPredecessor())
            return this.predecessor.getData().toString();
        else{
            return "None";
        }
    }

}