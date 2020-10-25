package my.spring.tacocloud.integrations.email;

import my.spring.tacocloud.Order;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<Order> {

    @Override
    protected AbstractIntegrationMessageBuilder<Order> doTransform(Message message) throws Exception {
        Order order = processPayload(message);
        return MessageBuilder.withPayload(order);
    }

    private Order processPayload(Message message) {
        return new Order();
    }
}
