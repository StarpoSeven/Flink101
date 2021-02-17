package Review.api.trainsform;

import Review.api.beans.UserAction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.Arrays;

public class MapDemo {
    private static int index = 1;

    public static void main(String[] args) throws Exception {

        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStream<UserAction> userActionDataStream = env.fromCollection(Arrays.asList(
                new UserAction("userID1", 1293984000L, "click", "productID1", 10),
                new UserAction("userID2", 1293984001L, "browse", "productID2", 8),
                new UserAction("userID1", 1293984002L, "click", "productID1", 10)
        ));

//        userActionDataStream.map(item -> {
//            int newPrice = item.getPrice() * 8;
//            return new UserAction(item.getUserID(),item.getTimeStamp(),item.getAction(),item.getProductID(),newPrice);
//        }).print();

        env.execute();







    }

}
