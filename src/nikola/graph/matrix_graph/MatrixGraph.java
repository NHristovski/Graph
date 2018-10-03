package nikola.graph.matrix_graph;

public class MatrixGraph{

    double[][] graph;

    public MatrixGraph(double[][] graph) {
        this.graph = graph;
    }

    public Matrix getMinDistanceFloydWarshall() {
        int N = graph.length;

        Integer[][] parentMatrix = new Integer[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                parentMatrix[i][j] = null;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (graph[i][j] != Double.MAX_VALUE / 2 && graph[i][j] != 0d) {
                    parentMatrix[i][j] = i;
                }
            }
        }


        for (int k = 0; k < N; ++k) {
            for (int i = 0; i < N; ++i) {
                for (int j = 0; j < N; ++j) {
                    if (graph[i][k] + graph[k][j] < graph[i][j]) {
                        parentMatrix[i][j] = parentMatrix[k][j];
                    }
                    graph[i][j] = Math.min(graph[i][j], graph[i][k] + graph[k][j]);
                }
            }
        }

        return new Matrix(graph, parentMatrix);
    }

    @Override
    public String toString() {
        if (graph.length > 0) {
            StringBuilder stringBuilder = new StringBuilder();

            for (double[] row : graph) {
                for (double elem : row) {
                    stringBuilder.append(elem).append(" ");
                }
                stringBuilder.append("\n");
            }
            return stringBuilder.toString();
        }
        else {
            return "Empty Graph";
        }
    }
}
