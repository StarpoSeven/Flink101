package zxp.wc.WordCount;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.util.Collector;
import scala.Int;

//批处理的word count
public class WordCount {

    //自定义类，实现FlatMapFunction接口
    public static class myFlatMapper implements FlatMapFunction<String, Tuple2<String,Integer>>  {

        public void flatMap(String s, Collector<Tuple2<String, Integer>> collector) throws Exception {
            //按空格分词
            String[] words = s.split(" ");

            //遍历所有word，包成二元组输出
            for (String word : words) {
                collector.collect(new Tuple2<String,Integer>(word,1));
            }
        }
    }



    public static void main(String[] args) throws Exception {
        //0: 创建批处理执行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        //1: 输入数据--从文件中读取数据
        String filePath = "/Users/zhuxingpo/Downloads/训练/FlinkTutorial/src/main/resources/WordCountInputFile.txt";
        //DataSource<String> stringDataSource = env.readTextFile(filePath); DataSource的父类是DataSet
        DataSet<String> inputDataSource = env.readTextFile(filePath);

        //2：处理数据--按照空格分词展开，转换成（word，1）这样的二元组进行统计
        DataSet<Tuple2<String, Integer>> resultSet = inputDataSource.flatMap(new myFlatMapper())
                .groupBy(0) //按照第一个位置的word分组
                .sum(1); //将第二个位置上的数据求和
        resultSet.print();
    }
}
