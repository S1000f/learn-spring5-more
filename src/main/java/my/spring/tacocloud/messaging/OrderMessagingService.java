package my.spring.tacocloud.messaging;

import my.spring.tacocloud.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
