package my.spring.tacocloud.messaging;

import my.spring.tacocloud.Order;

import javax.jms.JMSException;

public interface OrderReceiver {
    Order receiveOrder() throws JMSException;
}
