package my.spring.tacocloud.messaging.jms;

import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Order;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderListener {

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order) {
        // FIXME: business logic here using received message...
        log.info(order.getTacos().toString());
    }
}
