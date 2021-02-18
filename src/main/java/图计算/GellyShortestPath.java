package 图计算;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.graph.Edge;
import org.apache.flink.graph.Graph;
import org.apache.flink.graph.Vertex;
import org.apache.flink.graph.library.SingleSourceShortestPaths;
import org.apache.flink.types.NullValue;

public class GellyShortestPath {

    public static void main(String[] args) throws Exception {

        int sourceVertex = 3359851;

        int targetVertex = 19636959;
        int maxIterations = 10;

        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        DataSet<Follower> twitterFollowers = env.createInput(new DataSetInputFormat("/Users/zxp/Flink/twitter"));

        DataSet<Edge<Integer, NullValue>> twitterEdges = twitterFollowers
                .map(new MapFunction<Follower, Edge<Integer, NullValue>>() {
                    @Override
                    public Edge<Integer, NullValue> map(Follower value) throws Exception {
                        Edge<Integer, NullValue> edge = new Edge<>();
                        edge.setSource(value.getFollower());
                        edge.setTarget(value.getUser());

                        return edge;
                    }
                });

        Graph<Integer, NullValue, NullValue> followersGraph = Graph.fromDataSet(twitterEdges, env);
        // SSSP only works with weighted graphs
        Graph<Integer, NullValue, Double> weightedFollowersGraph = followersGraph.mapEdges(new MapFunction<Edge<Integer, NullValue>, Double>() {
            @Override
            public Double map(Edge<Integer, NullValue> edge) throws Exception {
                return 1.0;
            }
        });


        SingleSourceShortestPaths<Integer, NullValue> singleSourceShortestPaths = new SingleSourceShortestPaths<>(sourceVertex, maxIterations);
        DataSet<Vertex<Integer, Double>> result = singleSourceShortestPaths.run(weightedFollowersGraph);

        result.filter(vertex -> vertex.getId().equals(targetVertex))
                .print();
    }
}