package my.spring.tacocloud.reactive.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.create("http://localhost:8080");
  }

}
