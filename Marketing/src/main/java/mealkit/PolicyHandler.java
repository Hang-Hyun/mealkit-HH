package mealkit;

import mealkit.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    //추가
    @Autowired
    NotificationRepository notificationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCompleted_OrderNoti(@Payload PaymentCompleted paymentCompleted){

        if(paymentCompleted.isMe()){

            Notification notification = new Notification();
            notification.setId(paymentCompleted.getOrderId());
            notification.setOrderId(paymentCompleted.getOrderId());
            notification.setEmail("cc@cc.cc");
            notification.setRcvPhoneNumber("010-1111-1111");
            notification.setSndPhoneNumber("010-0000-0000");
            notification.setMessage("payment completed! prepare to ship");
            notification.setStatus("seller noti completed(payment completed)");

            notificationRepository.save(notification);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPaymentCancelled_OrderNoti(@Payload PaymentCancelled paymentCancelled){

        if(paymentCancelled.isMe()){
            System.out.println("##### listener OrderNoti : " + paymentCancelled.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryRequested_DeliveryStatNoti(@Payload DeliveryRequested deliveryRequested){

        if(deliveryRequested.isMe()){
            System.out.println("##### listener DeliveryStatNoti : " + deliveryRequested.toJson());
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCompleted_DeliveryStatNoti(@Payload DeliveryCompleted deliveryCompleted){

        if(deliveryCompleted.isMe()){
            Notification notification = new Notification();
            notification.setId(deliveryCompleted.getOrderId());
            notification.setEmail("buyer@cc.cc");
            notification.setRcvPhoneNumber("010-1111-1111");
            notification.setSndPhoneNumber("010-0000-0000");
            notification.setMessage("delivery completed! finish!");
            notification.setStatus("buyer final noti completed(delivery completed)");

            notificationRepository.save(notification);
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverDeliveryCancelled_DeliveryStatNoti(@Payload DeliveryCancelled deliveryCancelled){

        if(deliveryCancelled.isMe()){
            System.out.println("##### listener DeliveryStatNoti : " + deliveryCancelled.toJson());
        }
    }

}
