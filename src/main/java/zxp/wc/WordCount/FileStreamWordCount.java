package zxp.wc.WordCount;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class FileStreamWordCount {
    public static void main(String[] args) throws Exception {
        //0: 创建流处理执行环境
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();

        //1: 输入数据--从文件中读取数据
        String filePath = "/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/WordCountInputFile.txt";
        DataStream<String> inputStringDataStream = streamEnv.readTextFile(filePath);

        //2: 处理数据--基于数据流进行转换计算
        DataStream<Tuple2<String, Integer>> resultStream = inputStringDataStream.flatMap(new WordCount.myFlatMapper())
                .keyBy(0)
                .sum(1);

        //3: 输出数据，与批处理不同，如果只到这一步，没有任何的效果。流处理应该是定义操作，等待数据。
        resultStream.print();

        //4: 前面三步都是定义操作，从这一步开始执行
        streamEnv.execute();

    }
}
