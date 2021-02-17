package zxp.apitest.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//没有安装Kafka,跳过
public class Source3FromKafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.execute();
    }
}
