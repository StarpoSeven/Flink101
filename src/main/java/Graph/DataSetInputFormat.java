package Graph;

import org.apache.flink.api.common.io.DefaultInputSplitAssigner;
import org.apache.flink.api.common.io.RichInputFormat;
import org.apache.flink.api.common.io.statistics.BaseStatistics;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.FileStatus;
import org.apache.flink.core.io.InputSplit;
import org.apache.flink.core.io.InputSplitAssigner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystem;
import java.nio.file.Path;
import java.util.ArrayList;

public class DataSetInputFormat extends RichInputFormat<Follower, FileInputSplit> {
    private static final Logger logger = LoggerFactory.getLogger(DataSetInputFormat.class);
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
        FileSystem fileSystem = getFileSystem();
        FileStatus[] statuses = fileSystem.listStatus(new Path(inputPath));
        return new GraphStatistics(statuses.length);
    }

    private FileSystem getFileSystem() throws IOException {
        if (fileSystem == null) {
            try {
                fileSystem = FileSystem.get(new URI(inputPath));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return fileSystem;
    }

    @Override
    public FileInputSplit[] createInputSplits(int i) throws IOException {
        FileSystem fileSystem = getFileSystem();
        FileStatus[] statuses = fileSystem.listStatus(new Path(inputPath));
        logger.info("Found {} files", statuses.length);

        List<TweetFileInputSplit> splits = new ArrayList<>();
        for (int i = 0; i < statuses.length; i++) {
            FileStatus status = statuses[i];
            String fileName = status.getPath().getName();
            if (fileName.endsWith("edges")) {
                splits.add(new TweetFileInputSplit(i, status.getPath()));
            }
        }

        logger.info("Result number of splits: {}", splits.size());
        return splits.toArray(new TweetFileInputSplit[splits.size()]);
    }



    @Override
    public InputSplitAssigner getInputSplitAssigner(FileInputSplit[] fileInputSplits) {
        return new DefaultInputSplitAssigner(inputSplits);
    }

    @Override
    public void open(FileInputSplit fileInputSplit) throws IOException {
        FileSystem fileSystem = getFileSystem();
        this.reader = new BufferedReader(new InputStreamReader(fileSystem.open(split.getPath())));
        this.nextLine = reader.readLine();
    }

    @Override
    public boolean reachedEnd() throws IOException {
        return nextLine == null;
    }

    @Override
    public Follower nextRecord(Follower follower) throws IOException {
        String[] split = nextLine.split(" ");
        int userId = Integer.parseInt(split[0]);
        int followerId = Integer.parseInt(split[1]);

        reuse.setUser(userId);
        reuse.setFollower(followerId);
        nextLine = reader.readLine();

        return reuse;
    }

    @Override
    public void close() throws IOException {
        if (reader != null) {
            reader.close();
        }
    }

    private class GraphStatistics implements BaseStatistics {

        private long totalInputSize;

        public GraphStatistics(long totalInputSize) {
            this.totalInputSize = totalInputSize;
        }

        @Override
        public long getTotalInputSize() {
            return totalInputSize;
        }

        @Override
        public long getNumberOfRecords() {
            return BaseStatistics.NUM_RECORDS_UNKNOWN;
        }

        @Override
        public float getAverageRecordWidth() {
            return BaseStatistics.AVG_RECORD_BYTES_UNKNOWN;
        }
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