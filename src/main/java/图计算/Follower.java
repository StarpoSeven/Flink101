package 图计算;

import org.apache.flink.api.java.tuple.Tuple2;

public class Follower extends Tuple2<Integer, Integer> {

    public Follower() {
        super(null, null);
    }

    public Follower(int user, int follower) {
        super(user, follower);
    }

    public Integer getUser() {
        return f0;
    }

    public Integer getFollower() {
        return f1;
    }

    public void setUser(int user) {
        this.f0 = user;
    }

    public void setFollower(int follower) {
        this.f1 = follower;
    }
}
