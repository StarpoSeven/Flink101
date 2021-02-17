package Review.api.trainsform;

import Review.api.beans.SensorData;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;



public class Base {
    public static void main(String[] args) throws Exception {
        //获取环境
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//        DataStream<String> dataStream = env.readTextFile("/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/readTestFile");
//        DataStream<Integer> mapStream = dataStream.map(line -> {
//            return line.length();
//        });
//        DataStream<SensorData> flatMapStream = dataStream.flatMap((line,Collector<String> collection) -> {
//            String[] temp = line.split(",");
//            return new SensorData(temp[0],new Long(temp[1]),new Double(temp[2]));
//        });

//        DataStream<String> filterMapStream = dataStream.filter(line -> {
//            return line.startsWith("sensore_1");
//        });

//        mapStream.print("map");
//
//        env.execute();
//

    }
}
