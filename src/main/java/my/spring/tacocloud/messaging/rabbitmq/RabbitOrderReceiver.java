package my.spring.tacocloud.messaging.rabbitmq;

import my.spring.tacocloud.Order;
import my.spring.tacocloud.messaging.OrderReceiver;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitOrderReceiver implements OrderReceiver {

    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public RabbitOrderReceiver(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = rabbitTemplate.getMessageConverter();
    }

    @Override
    public Order receiveOrder() {
        Message message = rabbitTemplate.receive("tacocloud.orders");
        return message != null ? (Order) messageConverter.fromMessage(message) : null;
    }

    public Order receiveOrderConvertingAuto() {
        return (Order) rabbitTemplate.receiveAndConvert("tacocloud.orders");
    }

}
