package my.spring.tacocloud.messaging.jms;

import my.spring.tacocloud.Order;
import my.spring.tacocloud.messaging.OrderReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class JmsOrderReceiver implements OrderReceiver {

    private final JmsTemplate jms;
    private final MessageConverter converter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate, MessageConverter messageConverter) {
        this.jms = jmsTemplate;
        this.converter = messageConverter;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        Message message = jms.receive("tacocloud.order.queue");
        assert message != null;
        return (Order) converter.fromMessage(message);
    }

    public Order receiveJustOrder() {
        return (Order) jms.receiveAndConvert("tacocloud.order.queue");
    }
}
