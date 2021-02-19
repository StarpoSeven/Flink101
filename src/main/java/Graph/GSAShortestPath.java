package Graph;

import org.apache.flink.api.common.ProgramDescription;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;
import org.apache.flink.graph.gsa.ApplyFunction;
import org.apache.flink.graph.gsa.Neighbor;
import org.apache.flink.graph.gsa.SumFunction;
import org.apache.flink.graph.utils.Tuple3ToEdgeMap;

public class GSASingleSourceShortestPaths implements ProgramDescription {

    public static void main(String[] args) throws Exception {

        if (!parseParameters(args)) {
            return;
        }

        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        DataSet<Edge<Long, Double>> edges = getEdgeDataSet(env);

        Graph<Long, Double, Double> graph =
                Graph.fromDataSet(edges, new InitVertices(srcVertexId), env);

        Graph<Long, Double, Double> result =
                graph.runGatherSumApplyIteration(
                        new CalculateDistances(),
                        new ChooseMinDistance(),
                        new UpdateDistance(),
                        maxIterations);

        DataSet<Vertex<Long, Double>> singleSourceShortestPaths = result.getVertices();


        if (fileOutput) {
            singleSourceShortestPaths.writeAsCsv(outputPath, "\n", ",");

            env.execute("GSA Single Source Shortest Paths");
        } else {
            singleSourceShortestPaths.print();
        }
    }

    @SuppressWarnings("serial")
    private static final class InitVertices implements MapFunction<Long, Double> {

        private long srcId;

        public InitVertices(long srcId) {
            this.srcId = srcId;
        }

        public Double map(Long id) {
            if (id.equals(srcId)) {
                return 0.0;
            } else {
                return Double.POSITIVE_INFINITY;
            }
        }
    }

    @SuppressWarnings("serial")
    private static final class CalculateDistances extends GatherFunction<Double, Double, Double> {

        public Double gather(Neighbor<Double, Double> neighbor) {
            return neighbor.getNeighborValue() + neighbor.getEdgeValue();
        }
    }

    @SuppressWarnings("serial")
    private static final class ChooseMinDistance extends SumFunction<Double, Double, Double> {

        public Double sum(Double newValue, Double currentValue) {
            return Math.min(newValue, currentValue);
        }
    }

    @SuppressWarnings("serial")
    private static final class UpdateDistance extends ApplyFunction<Long, Double, Double> {

        public void apply(Double newDistance, Double oldDistance) {
            if (newDistance < oldDistance) {
                setResult(newDistance);
            }
        }
    }

    //  Util methods 暂缺

}