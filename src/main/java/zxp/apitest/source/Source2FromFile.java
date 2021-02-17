package zxp.apitest.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class Source2FromFile {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);
        DataStream<String> stringDataStream = env.readTextFile("/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/readTestFile");

        stringDataStream.print("stream from file");

        env.execute();

    }
}
