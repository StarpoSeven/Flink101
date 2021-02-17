package Review.api.source;

import Review.api.beans.SensorData;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import java.util.Properties;

public class SourceFrom3Kafka {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","localhost:9092");

        DataStream<String> dataStream = env.addSource(new FlinkKafkaConsumer<String>("sensor",new SimpleStringSchema(),properties));

        dataStream.print();

        env.execute();



    }

}
