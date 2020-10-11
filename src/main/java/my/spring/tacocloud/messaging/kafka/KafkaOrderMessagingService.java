//package my.spring.tacocloud.messaging.kafka;
//
//import my.spring.tacocloud.Order;
//import my.spring.tacocloud.messaging.OrderMessagingService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaOrderMessagingService implements OrderMessagingService {
//
//    private final KafkaTemplate<String, Order> kafkaTemplate;
//
//    @Autowired
//    public KafkaOrderMessagingService(KafkaTemplate kafkaTemplate) {
//        this.kafkaTemplate = kafkaTemplate;
//    }
//
//    @Override
//    public void sendOrder(Order order) {
//        kafkaTemplate.send("tacocloud.orders.topic", order);
//    }
//}
//
