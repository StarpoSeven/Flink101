//package zxp.apitest.Transform;
//
//import org.apache.flink.api.common.functions.MapFunction;
//import org.apache.flink.api.java.tuple.Tuple;
//import org.apache.flink.streaming.api.datastream.DataStream;
//import org.apache.flink.streaming.api.datastream.KeyedStream;
//import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
//import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
//import zxp.apitest.beans.SensorReading;
//
//public class RollingAggregation {
//    public static void main(String[] args) throws Exception {
//        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
//
//        //从文件读取数据
//        DataStream<String> inputStringDataStream = env.readTextFile("/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/readTestFile");
//
//
//        //转换成SensorReading类型
////        DataStream<SensorReading> mapStream = stringDataStream.map(new MapFunction<String, SensorReading>() {
////            public SensorReading map(String s) throws Exception {
////                String[] fields = s.split(",");
////                return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
////            }
////        });
////        DataStream<SensorReading> mapStream = inputStringDataStream.map(line -> {
////            String[] fields = line.split(",");
////            return new SensorReading(fields[0],new Long(fields[1]),new Double(fields[2]));
////        });
//
//
//        //分组，为什么第一个不行，然后3方法引用，代码可读性不行不加括号，否则变成方法调用了
//
//        // 1：KeyedStream<SensorReading, Tuple> sensorReadingTupleKeyedStream = mapStream.keyBy(0);
//        KeyedStream<SensorReading, Tuple> keyedStream = mapStream.keyBy("id");
//
//        // 3：KeyedStream<SensorReading, Tuple> sensorReadingTupleKeyedStream = mapStream.keyBy(SensorReading::getId);
//
//
//        //滚动聚合，取当前最大的温度
//        SingleOutputStreamOperator<SensorReading> resultStream = keyedStream.max("temperature");
//        resultStream.print();
//
//        env.execute();
//
//    }
//}
