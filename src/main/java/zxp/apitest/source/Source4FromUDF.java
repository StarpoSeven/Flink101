package zxp.apitest.source;

import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.SourceFunction;
import zxp.apitest.beans.SensorReading;

import java.util.HashMap;
import java.util.Random;

public class Source4FromUDF {
    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<SensorReading> dataStream =  env.addSource(new mySensorSource());
        dataStream.print();
        env.execute();
    }

    public static class mySensorSource implements SourceFunction<SensorReading> {
        //定义一个标志位 用来控制数据的产生
        private boolean button = true;

        public void run(SourceContext<SensorReading> sourceContext) throws Exception {
            //定义一个随机数发生器
            Random random = new Random();

            //设置10个传感器的初始温度
            HashMap<String,Double> sensorMap = new HashMap<String, Double>();
            for (int i = 1; i <= 10; i++) {

                sensorMap.put("Sensor" + i, 60 + random.nextGaussian() * 20);
            }


            while (button) {
                for (String sensorId : sensorMap.keySet()) {
                    //在当前温度基础上随机波动
                    Double updataData = sensorMap.get(sensorId) + random.nextGaussian();
                    sensorMap.put(sensorId,updataData);
                    sourceContext.collect(new SensorReading(sensorId,System.currentTimeMillis(),updataData));
                }
            }

            //控制输出频率
            Thread.sleep(3000L);
        }

        public void cancel() {
            button = false;
        }
    }



}



