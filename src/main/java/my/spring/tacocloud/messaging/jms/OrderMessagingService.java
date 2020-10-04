package my.spring.tacocloud.messaging.jms;

import my.spring.tacocloud.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
