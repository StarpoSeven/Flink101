package Graph;

import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.Path;

public class DataSetInputFormat extends RichInputFormat<Follower, FileInputSplit> {
    private static final Logger logger = LoggerFactory.getLogger(StanfordTweetsDataSetInputFormat.class);
    private transient FileSystem fileSystem;
    private transient BufferedReader reader;
    private final String inputPath;
    private String nextLine;

    public DataSetInputFormat(String path) {
        this.inputPath = path;
    }


    @Override
    public void configure(Configuration configuration) {

    }

    @Override
    public BaseStatistics getStatistics(BaseStatistics baseStatistics) throws IOException {
        return null;
    }

    @Override
    public FileInputSplit[] createInputSplits(int i) throws IOException {
        return new FileInputSplit[0];
    }

    @Override
    public InputSplitAssigner getInputSplitAssigner(FileInputSplit[] fileInputSplits) {
        return null;
    }

    @Override
    public void open(FileInputSplit fileInputSplit) throws IOException {

    }

    @Override
    public boolean reachedEnd() throws IOException {
        return false;
    }

    @Override
    public Follower nextRecord(Follower follower) throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}



class FileInputSplit implements InputSplit {

    private final int splitNumber;
    private final Path path;

    public FileInputSplit(int splitNumber, Path path) {
        this.splitNumber = splitNumber;
        this.path = path;
    }

    @Override
    public int getSplitNumber() {
        return splitNumber;
    }

    public Path getPath() {
        return path;
    }
}