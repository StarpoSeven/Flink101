package zxp.wc.WordCount;

import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SocketStreamWordCount {
    public static void main(String[] args) throws Exception {
        //0: 创建流处理执行环境
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();

        //1: 输入数据--从socket文本流中读取数据
        //DataStream<String> inputStringDataStream = streamEnv.socketTextStream("localhost",7777);
        //为了避免被直接写死主机名，端口号，使用Flink自带的Parameter tool工具从程序启动参数中提取主机名
        ParameterTool parameterTool = ParameterTool.fromArgs(args);
        String host = parameterTool.get("host");
        int port = parameterTool.getInt("port");
        DataStream<String> inputStringDataStream = streamEnv.socketTextStream(host,port);


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
