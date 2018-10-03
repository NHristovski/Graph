package nikola.graph;

import nikola.graph.list_graph.ListGraph;

import static java.lang.System.out;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ListGraph<Integer> graph = new ListGraph<>(4,7);

        for (int i = 1; i <= 4; i++){
            graph.addVertex(i);
        }

        graph.addDirectedEdge(1,4,2);
        graph.addDirectedEdge(1,2,1);
        graph.addDirectedEdge(1,3,2);
        graph.addDirectedEdge(2,3,2);
        graph.addDirectedEdge(2,4,4);
        graph.addDirectedEdge(3,4,2);
        graph.addDirectedEdge(4,2,5);

        out.println(graph);

        out.print(" --------------------------------- \n");
        out.print(graph.convertToMatrixGraph());

    }
}
