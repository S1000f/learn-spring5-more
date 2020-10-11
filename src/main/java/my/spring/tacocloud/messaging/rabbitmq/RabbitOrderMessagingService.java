package my.spring.tacocloud.messaging.rabbitmq;

import my.spring.tacocloud.Order;
import my.spring.tacocloud.messaging.OrderMessagingService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitOrderMessagingService implements OrderMessagingService {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitOrderMessagingService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        MessageConverter converter = rabbitTemplate.getMessageConverter();
        MessageProperties properties = new MessageProperties();

        // 전송할 메시지의 헤더를 커스터마이징 할 수 있다
        properties.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = converter.toMessage(order, properties);

        // 라우팅 키만 지정하여 전송하므로 기본 거래소를 이용하게 된다
        rabbitTemplate.send("tacocloud.orders", message);
    }

    // 메시지 컨버팅까지 자동으로 해서 전송한다
    public void sendOrderAutoConverting(Order order) {
        rabbitTemplate.convertAndSend("tacocloud.orders", order);
    }

    /* convertAndSend 메서드 사용시 jmsTemplate 처럼 포스트 프로세서를 전달해야 한다. 공용으로 사용되는
    * 후처리 프로세싱이라면 공용 메소드로 분리하는게 좋다
    *  */
    public void sendOrderWithCustomHeader(Order order) {
        rabbitTemplate.convertAndSend("tacocloud.orders", order, message -> {
                    MessageProperties properties = message.getMessageProperties();
                    properties.setHeader("X_ORDER_SOURCE", "WEB");
                    return message;
        });
    }
}
