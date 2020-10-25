package my.spring.tacocloud.integrations.email;

import my.spring.tacocloud.Order;
import org.springframework.integration.handler.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class OrderSubmitMessageHandler implements GenericHandler<Order> {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Object handle(Order order, MessageHeaders messageHeaders) {
        restTemplate.postForObject("http://localhost:8080/api/", order, String.class);
        return null;
    }
}
