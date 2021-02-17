package 图计算;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.GraphAlgorithm;
import org.apache.flink.graph.Vertex;

import java.util.Arrays;
import java.util.List;

public class PerlShortestPath {
    public static void main(String[] args) {

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        List<Vertex<Integer, String>> vertices = Arrays.asList(
                new Vertex<>(1, "1"),
                new Vertex<>(2, "2"),
                new Vertex<>(3, "3"),
                new Vertex<>(4, "4"),
                new Vertex<>(5, "5")
        );

        List<Edge<Integer, Double>> edges = Arrays.asList(
                new Edge<>(1, 2, 2.0),
                new Edge<>(1, 3, 7.0),
                new Edge<>(2, 3, 2.0),
                new Edge<>(3, 2, 5.0),
                new Edge<>(2, 4, 4.0),
                new Edge<>(3, 4, 6.0),
                new Edge<>(3, 5, 3.0),
                new Edge<>(4, 5, 4.0),
                new Edge<>(5, 4, 1.0),
                new Edge<>(5, 1, 8.0)
        );

        Graph<Integer, String, Double> graph = Graph.fromCollection(vertices, edges, env);

        graph.run(new ShortestPath<>(1, 10)).print();

    }
}

class ShortestPath<K, VV> implements GraphAlgorithm<K, VV, Double, DataSet<Vertex<K, Double>>> {

    private final K sourceVertex;
    private final int maxIterations;

    public ShortestPath(K sourceVertex, int maxIterations) {
        this.sourceVertex = sourceVertex;
        this.maxIterations = maxIterations;
    }

    @Override
    public DataSet<Vertex<K, Double>> run(Graph<K, VV, Double> graph) throws Exception {
        Graph<K, Double, Double> resultGraph = graph.mapVertices(new ShortestPathInit<>(sourceVertex))
                .runVertexCentricIteration(new ShortestPathComputeFunction(sourceVertex),
                        new ShortestPathCombiner(),
                        maxIterations);
        return resultGraph.getVertices();
    }

    private static class ShortestPathInit<K, VV> implements MapFunction<Vertex<K,VV>, Double> {

        private final K sourceVertex;

        public ShortestPathInit(K sourceVertex) {
            this.sourceVertex = sourceVertex;
        }

        @Override
        public Double map(Vertex<K, VV> vertex) throws Exception {
            if (vertex.getId().equals(sourceVertex)) {
                return 0d;
            }
            return Double.MAX_VALUE;
        }
    }
}

