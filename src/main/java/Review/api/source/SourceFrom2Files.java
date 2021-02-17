package Review.api.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SourceFrom2Files {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        String path = "/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/WordCountInputFile.txt";

        DataStream<String> dataStream = env.readTextFile(path);

        dataStream.print();

        env.execute();

    }
}
