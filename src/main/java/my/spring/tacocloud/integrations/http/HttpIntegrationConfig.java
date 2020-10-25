package my.spring.tacocloud.integrations.http;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.integration.annotation.Router;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.http.dsl.Http;
import org.springframework.integration.router.AbstractMessageRouter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;

import java.util.Collection;

@EnableIntegration
@Configuration
public class HttpIntegrationConfig {

//    @Bean
    public IntegrationFlow httpFlow() {
        return IntegrationFlows.from(Http.inboundChannelAdapter("/"))
                .get();
    }

    @Bean
    public MessageChannel httpRequestChannel() {
        return new DirectChannel();
    }

    @Bean
    @Router(inputChannel = "httpRequestChannel")
    public AbstractMessageRouter endpointRoute() {
        return new AbstractMessageRouter() {
            @Override
            protected Collection<MessageChannel> determineTargetChannels(Message<?> message) {
                MessageHeaders messageHeaders = message.getHeaders();
                HttpHeaders httpHeaders = (HttpHeaders) messageHeaders.get("httpHeaders");

                if (httpHeaders.getLocation().getPath().equals("/user/login")) {

                }




                return null;
            }
        };
    }
}
