package zxp.apitest.source;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import zxp.apitest.beans.SensorReading;

import java.util.Arrays;

public class Source1FromCollection {
    public static void main(String[] args) throws Exception {
        //创建环境
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.setParallelism(1);
        //从集合中读取数据
        DataStream<SensorReading> dataStream = env.fromCollection(Arrays.asList(
                new SensorReading("sensor177", 103824L, 37.3),
                new SensorReading("sensor23", 987321L, 38.2),
                new SensorReading("sensor1", 786587L, 35.9)));

        DataStream<SensorReading> sensorReadingDataStream = env.fromElements(
                new SensorReading("sensor177", 103824L, 37.3),
                new SensorReading("sensor23", 987321L, 38.2),
                new SensorReading("sensor1", 786587L, 35.9));

        //打印数据
        dataStream.print("fromCollection");
        dataStream.print("fromElements");

        //执行
        env.execute();


    }
}
