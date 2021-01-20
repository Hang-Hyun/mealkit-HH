package mealkit;

public class PaymentCancelled extends AbstractEvent {

    private Long id;

    public PaymentCancelled(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
