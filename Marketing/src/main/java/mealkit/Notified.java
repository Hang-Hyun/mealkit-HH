package mealkit;

public class Notified extends AbstractEvent {

    private Long id;

    public Notified(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
