package Review.api.source;

import Review.api.beans.SensorData;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class SourceFrom1Collection {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        SensorData data1 = new SensorData("177",32479L,35.3);
        SensorData data2 = new SensorData("23",889473L,37.2);
        SensorData data3 = new SensorData("30",975642L,40.9);

        DataStream<SensorData> sensorDataDataStream = env.fromCollection(Arrays.asList(data1, data2, data3));
        sensorDataDataStream.print("from sensor");

        DataStream<Integer> integerDataStream = env.fromElements(24, 51, 32, 90);
        integerDataStream.print("from integer");

        env.execute();
    }

    public static void f() {


        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<String> s = env.readTextFile("");
        s.print();
        try {
            env.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }







}
