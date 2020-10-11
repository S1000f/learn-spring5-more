package my.spring.tacocloud.messaging;

import my.spring.tacocloud.Order;
import org.apache.activemq.artemis.jms.client.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;

import javax.jms.Destination;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class MessagingConfig {

    @Bean
    public Destination defaultOrderQueue() {
        return new ActiveMQQueue("tacocloud.order.queue");
    }

    @Bean
    public MessageConverter messageConverter() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        Map<String, Class<?>> typeIdMapping = new HashMap<>();
        typeIdMapping.put("order", Order.class);
        messageConverter.setTypeIdMappings(typeIdMapping);

        return  messageConverter;
    }

    @Bean(autowireCandidate = false)
    public MappingJackson2MessageConverter messageConverter2() {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setTypeIdPropertyName("_typeId");

        return messageConverter;
    }

}
