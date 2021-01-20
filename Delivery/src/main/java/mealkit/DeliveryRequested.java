package mealkit;

public class DeliveryRequested extends AbstractEvent {

    private Long id;
    private Long orderId;
    private String status;

    public DeliveryRequested(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderid() {
        return orderId;
    }

    public void setOrderid(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
