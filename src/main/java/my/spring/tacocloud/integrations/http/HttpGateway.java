package my.spring.tacocloud.integrations.http;

import org.springframework.http.*;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@MessagingGateway(defaultRequestChannel = "httpRequestChannel", defaultReplyChannel = "httpResponseChannel")
@Component
public interface HttpGateway {
    ResponseEntity<?> flow(@Header(name = "httpHeaders") HttpHeaders httpHeaders, RequestEntity<?> requestEntity);
}
