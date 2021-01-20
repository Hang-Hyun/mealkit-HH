package mealkit;

public class PaymentCompleted extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String cardnumber;
    private String cardowner;
    private String approvednumber;
    private String status;
    private String address;

    public PaymentCompleted(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getCardnumber() {
        return cardnumber;
    }

    public void setCardnumber(String cardnumber) {
        this.cardnumber = cardnumber;
    }
    public String getCardowner() {
        return cardowner;
    }

    public void setCardowner(String cardowner) {
        this.cardowner = cardowner;
    }
    public String getApprovednumber() {
        return approvednumber;
    }

    public void setApprovednumber(String approvednumber) {
        this.approvednumber = approvednumber;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
