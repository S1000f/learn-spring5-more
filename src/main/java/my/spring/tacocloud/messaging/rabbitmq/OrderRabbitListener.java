package my.spring.tacocloud.messaging.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderRabbitListener {

    @RabbitListener(queues = "tacocloud.orders")
    public void receiveOrder(Order order) {
        log.info("received order : " + order.getTacos().toString());
    }
}
