package Review.api.beans;

public class UserAction {
    private String userID;
    private Long timeStamp;
    private String action;
    private String productID;
    private int price;

    public UserAction(String userID, Long timeStamp, String action, String productID, int price) {
        this.userID = userID;
        this.timeStamp = timeStamp;
        this.action = action;
        this.productID = productID;
        this.price = price;
    }

    @Override
    public String toString() {
        return "UserAction{" +
                "userID='" + userID + '\'' +
                ", timeStamp=" + timeStamp +
                ", action='" + action + '\'' +
                ", productID='" + productID + '\'' +
                ", price=" + price +
                '}';
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
