package my.spring.tacocloud.reactive.client;

import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Ingredient;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class ReactiveClient {

  public void get() {
    Long ingredientId = 10L;
    Mono<Ingredient> ingredientMono = WebClient.create()
        .get()
        .uri("http://localhost:8080/ingredients/{id}", ingredientId)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe();
  }

}
