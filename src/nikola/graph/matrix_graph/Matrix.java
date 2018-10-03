package nikola.graph.matrix_graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Matrix {
    private double[][] adjMatrix;
    private Integer[][] parrentMatrix;

    public Matrix(double[][] adjMatrix, Integer[][] parrentMatrix) {
        this.adjMatrix = adjMatrix;
        this.parrentMatrix = parrentMatrix;
    }

    public double[][] getAdjMatrix() {
        return adjMatrix;
    }

    public Integer[][] getParrentMatrix() {
        return parrentMatrix;
    }

    public List<Integer> getPathBetween(int source, int destination) {
        int start = source - 1;
        int finish = destination - 1;

        List<Integer> path = new ArrayList<>();

        while (parrentMatrix[start][finish] != start) {
            path.add(parrentMatrix[start][finish] + 1);
            finish = parrentMatrix[start][finish];
        }
        path.add(parrentMatrix[start][finish] + 1);
        Collections.reverse(path);

        return path;
    }

    public double getDistanceBetween(int source, int destination) {
        int start = source - 1;
        int finish = destination - 1;
        return this.adjMatrix[start][finish];
    }
}

