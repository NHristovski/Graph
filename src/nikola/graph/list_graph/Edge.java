package nikola.graph.list_graph;

import java.util.Objects;

public class Edge<E extends Comparable<E>>{

    private Vertex<E> source;
    private Vertex<E> destination;
    private double weight;

    public Edge(Vertex<E> source, Vertex<E> destination, double weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }

    public Vertex<E> getSource() {
        return source;
    }

    public void setSource(Vertex<E> source) {
        this.source = source;
    }

    public Vertex<E> getDestination() {
        return destination;
    }

    public void setDestination(Vertex<E> destination) {
        this.destination = destination;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString(){
        return new StringBuilder()
                .append("Edge { from: ")
                .append(this.source.toString())
                .append("\t to: ")
                .append(this.destination.toString())
                .append("\t weight: ")
                .append(this.weight)
                .append(" }")
                .toString();

    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.source);
        hash = 11 * hash + Objects.hashCode(this.destination);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge<?> other = (Edge<?>) obj;

        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        return true;
    }

}
