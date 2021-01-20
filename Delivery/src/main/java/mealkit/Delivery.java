package mealkit;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Delivery_table")
public class Delivery {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String address;
    private String status;

    @PostPersist
    public void onPostPersist(){
//        DeliveryCompleted deliveryCompleted = new DeliveryCompleted();
//        BeanUtils.copyProperties(this, deliveryCompleted);
//        deliveryCompleted.publishAfterCommit();


        DeliveryRequested deliveryRequested = new DeliveryRequested();
        BeanUtils.copyProperties(this, deliveryRequested);
        
        System.out.println("********************");
        System.out.println("*****"+this.getOrderId()+"*******");
        System.out.println("*****"+deliveryRequested.toJson()+"*******");
        System.out.println("********************");
        deliveryRequested.setOrderid(this.getOrderId());
        deliveryRequested.publishAfterCommit();


    }

    @PostUpdate
    public void onPostUpdate(){

        if(this.status.equals("order canceled-delivery!!")) {
            DeliveryCancelled deliveryCancelled = new DeliveryCancelled();
            BeanUtils.copyProperties(this, deliveryCancelled);

            System.out.println("****************************");
            System.out.println(deliveryCancelled.toJson());
            System.out.println("****************************");
            deliveryCancelled.setOrderid(this.getOrderId());
            deliveryCancelled.publishAfterCommit();

        }
        else {
            DeliveryCompleted deliveryCompleted = new DeliveryCompleted();
            BeanUtils.copyProperties(this, deliveryCompleted);
            deliveryCompleted.setOrderid(this.getOrderId());
            deliveryCompleted.publishAfterCommit();
        }

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
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
