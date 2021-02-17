package Review.api.trainsform;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

public class FlatMapDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<String> dataSource = env.fromElements("take what you are doing seriously",
                "enjoy what you are learning",
                "training, thinking, rest");
        SingleOutputStreamOperator<Object> res = dataSource.flatMap(new FlatMapFunction<String, Object>() {
            @Override
            public void flatMap(String s, Collector<Object> collector) throws Exception {
                if (s.contains("learning")) {
                    String[] words = s.split(" ");
                    for (String word : words) {
                        collector.collect(word);
                    }
                }
            }
        });

        res.print();
        env.execute();


    }
}
