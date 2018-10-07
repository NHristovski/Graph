package nikola.rotate_list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import static java.lang.System.out;

public class RotationalLinkedList<E extends Comparable<E>>
                          implements Rotatable,Iterable<E> {

    // =======================================================================
    //                               Constants
    // =======================================================================
    private static final String ROTATIONS_ERROR_MSG = "Times must be >= 1";

    // =======================================================================
    //                                 Fields
    // =======================================================================
    private Node<E> first;
    private Node<E> last;
    private int size;

    // =======================================================================
    //                              Constructors
    // =======================================================================
    public RotationalLinkedList(){
    }

    public RotationalLinkedList(Collection<E> elements){
        first = new Node<>();
        Node<E> previousNode = first;
        size = elements.size();

        for (E elem : elements){
            Node<E> currentNode = new Node<>(elem,null);

            currentNode.setPrevious(previousNode);
            previousNode.setNext(currentNode);

            previousNode = currentNode;
        }

        last = previousNode;
        first = first.getNext();
        first.setPrevious(null);
    }

    @SafeVarargs
    public RotationalLinkedList(E... elements){
        first = new Node<>(null,null);
        Node<E> previousNode = first;
        size = elements.length;

        for (E elem : elements){
            Node<E> currentNode = new Node<>(elem,null);

            currentNode.setPrevious(previousNode);
            previousNode.setNext(currentNode);

            previousNode = currentNode;
        }

        last = previousNode;
        first = first.getNext();
        first.setPrevious(null);
    }

    // =======================================================================
    //                                 Additions
    // =======================================================================
    private void handleFirstAddition(E data) {
        first = new Node<>(data);
        last = first;
    }

    public void addFirst(E data){
        if (first == null){
            handleFirstAddition(data);
        }else {
            Node<E> nodeToAdd = new Node<>(data, first);
            first.setPrevious(nodeToAdd);

            first = first.getPrevious();
        }
        size++;
    }

    public void addLast(E data){
        if (last == null){
            handleFirstAddition(data);
        }else {
            Node<E> nodeToAdd = new Node<>(data, null, last);
            last.setNext(nodeToAdd);

            last = last.getNext();
        }
        size++;
    }

    // =======================================================================
    //                                 Delete
    // =======================================================================
    public E deleteLast(){
        E elem = null;
        if (last != null){
            elem = last.getData();
            Node<E> preLast = last.getPrevious();
            if (preLast != null){
                preLast.setNext(null);
            }
            last = preLast;
            size--;
        }
        return elem;
    }

    public E deleteFirst(){
        E elem = null;
        if (first != null){
            elem = first.getData();

            Node<E> second = first.getNext();
            if (second != null){
                second.setPrevious(null);
            }
            first = second;
            size--;
        }
        return elem;
    }

    // =======================================================================
    //                                 Insertions
    // =======================================================================
    /**
     * TimeComplexity: O(n/2)
     * throws IndexOutOfBoundsException
     * @param position ZERO-BASED
     *
     */
    public void insertBefore(int position,E data) throws IndexOutOfBoundsException{
        requireNonNegative(position);

        if (position > size){
            throw new IndexOutOfBoundsException(
                            "You entered the index: " + position +
                            " but the list's size is " + getSize());

        }else if (position == size){
            addLast(data);

        }else if (position == 0){
            addFirst(data);

        }else {
            Node<E> node = getNodeAtPosition(position);
            Node<E> nodeToAdd = new Node<>(data, node, node.getPrevious());
            node.getPrevious().setNext(nodeToAdd);
            node.setPrevious(nodeToAdd);
            size++;

        }
    }

    /**
     * TimeComplexity: O(n/2)
     * @param position ZERO-BASED
     */
    public void insertAfter(int position,E data) throws IndexOutOfBoundsException{
        insertBefore(position + 1,data);
    }

    // =======================================================================
    //                                 Rotations
    // =======================================================================
    public void rotateLeftOnce(){
        E elem = deleteFirst();
        if (elem != null) {
            addLast(elem);
        }
    }
    public void rotateRightOnce(){
        E elem = deleteLast();
        if (elem != null) {
            addFirst(elem);
        }
    }
    @Override
    public void rotateLeft(int times) throws IllegalArgumentException{
        if (times >= 1) {
            int leftRotations = times % size;

            if (leftRotations > (size / 2)) {
                int rightRotations = size - leftRotations;

                while (rightRotations > 0){
                    rotateRightOnce();
                    rightRotations--;
                }
            }else{
                while (leftRotations > 0){
                    rotateLeftOnce();
                    leftRotations--;
                }
            }
        }else {
            throw new IllegalArgumentException(ROTATIONS_ERROR_MSG);
        }
    }

    @Override
    public void rotateRight(int times) throws IllegalArgumentException{

        if (times >= 1) {
            int rightRotations = times % size;

            if (rightRotations > (size / 2)) {
                int leftRotations = size - rightRotations;

                while (leftRotations > 0){
                    rotateLeftOnce();
                    leftRotations--;
                }
            }else{
                while (rightRotations > 0){
                    rotateRightOnce();
                    rightRotations--;
                }
            }
        }else {
            throw new IllegalArgumentException(ROTATIONS_ERROR_MSG);
        }
    }

    // =======================================================================
    //                                 Getters
    // =======================================================================
    /**
     * TimeComplexity: O(n/2)
     * @param position ZERO-BASED
     */
    public E get(int position){
        requireNonNegative(position);

        if (position >= size) {
            throw new IndexOutOfBoundsException(
                    "You entered the index: " + position +
                            " but the list's size is " + getSize()
                            + ".ITS ZERO BASED!");
        }

        return getNodeAtPosition(position).getData();
    }

    public int getSize() {
        return size;
    }

    private Node<E> getNodeAtPosition(int position) {
        if (position < (size / 2)){
            return getNodeFromStart(position);
        }else{
            int endPosition = size - 1 - position;
            return getNodeFromEnd(endPosition);
        }
    }

    private Node<E> getNodeFromEnd(int position) {
        Node<E> currentNode = last;
        while (position > 0){
            currentNode = currentNode.getPrevious();
            position--;
        }
        return currentNode;
    }

    private Node<E> getNodeFromStart(int position) {
        Node<E> currentNode = first;
        while (position > 0){
            currentNode = currentNode.getNext();
            position--;
        }
        return currentNode;
    }


    // =======================================================================
    //                                 Conversions
    // =======================================================================
    @Override
    public String toString() {
        StringBuilder list = new StringBuilder();
        list.append("RotationalLinkedList: {\nL -> R: [");
        Node<E> current = first;
        if (current != null){
            list.append(current.getData());

            while (current.hasNext()){
                current = current.getNext();
                list.append(", ").append(current.getData());
            }

            list.append("]\nR -> L: [");
            list.append(current.getData());

            while (current.hasPrevious()){
                current = current.getPrevious();
                list.append(", ").append(current.getData());
            }

            list.append("]");
        }else{
            list.append("]\nR -> L: []");
        }
        list.append("\n}");
        return list.toString();
    }

    public ArrayList<E> toArrayList(){
        ArrayList<E> arrayList = new ArrayList<>(size);

        for(E elem : this){
            arrayList.add(elem);
        }

        return arrayList;
    }

    // =======================================================================
    //                                Iterators
    // =======================================================================
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private Node<E> currentNode = new Node<>(null,first);

            @Override
            public boolean hasNext() {
                return currentNode.hasNext();
            }

            @Override
            public E next() {
                currentNode = currentNode.getNext();
                return currentNode.getData();
            }
        };
    }

    public Iterator<E> reverseIterator(){
        return new Iterator<E>() {
            Node<E> currentNode = new Node<>(null,null,last);
            @Override
            public boolean hasNext() {
                return currentNode.hasPrevious();
            }

            @Override
            public E next() {
                currentNode = currentNode.getPrevious();
                return currentNode.getData();
            }
        };
    }

    // =======================================================================
    //                                Utils
    // =======================================================================
    private void requireNonNegative(int position) throws IndexOutOfBoundsException {
        if (position < 0){
            throw new IndexOutOfBoundsException("The index  cannot be less than 0");
        }
    }

    public RotationalLinkedList<E> sort(){
        ArrayList<E> arrayList = this.toArrayList();
        Collections.sort(arrayList);

        return new RotationalLinkedList<>(arrayList);
    }

    public RotationalLinkedList<E> sort(Comparator<E> comparator){
        ArrayList<E> arrayList = this.toArrayList();
        arrayList.sort(comparator);

        return new RotationalLinkedList<>(arrayList);
    }

    // =======================================================================
    //                                Testing
    // =======================================================================
    public static void main(String[] args) {

        RotationalLinkedList<Integer> rotationalList = new RotationalLinkedList<>();
        out.println("Empy list: " + rotationalList);

        rotationalList.addLast(2);
        out.println("After add last 2: " + rotationalList);

        rotationalList.addLast(3);
        out.println("After add last 3: " + rotationalList);

        rotationalList.addLast(1);
        out.println("After add last 1: " + rotationalList);

        rotationalList.addFirst(4);
        rotationalList.addFirst(3);
        rotationalList.addFirst(1);
        out.println("After add first: " + rotationalList);

        rotationalList.rotateLeftOnce();
        out.println("Rotate left once: " + rotationalList);

        rotationalList.rotateRightOnce();
        out.println("Rotate right once: " + rotationalList);

        rotationalList.rotateLeft(1_000);
        out.println("Rotate left: " + rotationalList);

        Iterator<Integer> reverseIterator = rotationalList.reverseIterator();

        while (reverseIterator.hasNext()){
            Integer element = reverseIterator.next();
            out.print(element + ", ");
        }
        out.println();

        out.println("sorted: " + rotationalList.sort());

        out.println("Element at index 2: " + rotationalList.get(2));

        rotationalList.insertBefore(1,10);
        rotationalList.insertBefore(3,20);

        out.println("After inserting: " + rotationalList);


    }
}
