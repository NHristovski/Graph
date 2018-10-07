package nikola.rotate_list;

public class Node<E extends Comparable<E>> implements Comparable<Node<E>>{

    E data;
    Node<E> next;
    Node<E> previous;

    @Override
    public int compareTo(Node<E> otherNode) {
        return this.data.compareTo(otherNode.data);
    }
    public Node(){
    }

    public Node(E data){
        this.data = data;
    }
    public Node(E data,Node<E> next){
        this(data);
        this.next = next;
    }
    public Node(E data,Node<E> next,Node<E> previous){
        this(data,next);
        this.previous = previous;
    }

    public E getData() {
        return data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public boolean hasNext(){
        return next != null;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public boolean hasPrevious(){
        return previous != null;
    }

    public Node<E> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<E> previous) {
        this.previous = previous;
    }
}
