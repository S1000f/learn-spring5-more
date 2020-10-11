package my.spring.tacocloud.messaging.jms;

import my.spring.tacocloud.Order;
import my.spring.tacocloud.messaging.OrderMessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.Message;

@Service
public class JmsOrderMessagingService implements OrderMessagingService {

    private final JmsTemplate jmsTemplate;
    private final Destination orderQueue;


    @Autowired
    public JmsOrderMessagingService(JmsTemplate jmsTemplate, Destination destination) {
        this.jmsTemplate = jmsTemplate;
        this.orderQueue = destination;
    }

    @Override
    public void sendOrder(Order order) {
        jmsTemplate.send(session -> session.createObjectMessage(order));
    }

    public void sendOrderWithDestination(Order order) {
        jmsTemplate.send("tacocloud.order.queue", session -> session.createObjectMessage(order));
    }

    public void sendOrderByDestination(Order order) {
        jmsTemplate.send(orderQueue, session -> session.createObjectMessage(order));
    }

    public void sendOrderWithConverter(Order order) {
        jmsTemplate.convertAndSend("tacocloud.order.queue", order);
    }

    public void sendOrderWithCustomHeader(Order order) {
        jmsTemplate.send(session -> {
            Message message = session.createObjectMessage(order);
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }

    public void sendOrderWithCustomHeaderByConverter(Order order) {
        jmsTemplate.convertAndSend(order, message -> {
            message.setStringProperty("X_ORDER_SOURCE", "WEB");
            return message;
        });
    }
}
