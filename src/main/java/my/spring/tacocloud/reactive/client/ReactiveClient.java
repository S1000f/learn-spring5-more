package my.spring.tacocloud.reactive.client;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Ingredient;
import my.spring.tacocloud.Ingredient.Type;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class ReactiveClient {

  private final WebClient defaultWebClient;

  public void get() {
    Long ingredientId = 10L;
    Mono<Ingredient> ingredientMono = WebClient.create()
        .get()
        .uri("http://localhost:8080/ingredients/{id}", ingredientId)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe();
  }

  public void getIngredientById(String ingredientId) {
    Mono<Ingredient> ingredientMono = defaultWebClient.get()
        .uri("/ingredients/{id}", ingredientId)
        .retrieve()
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe();
  }

  public void timeout() {
    Flux<Ingredient> ingredientFlux = defaultWebClient.get()
        .uri("/ingredients")
        .retrieve()
        .bodyToFlux(Ingredient.class);

    ingredientFlux.timeout(Duration.ofSeconds(1L))
        .subscribe();
  }

  public void post() {
    Mono<Ingredient> ingredientMono = Mono.just(new Ingredient("test", "name", Type.WRAP));

    Mono<Ingredient> result = defaultWebClient.post()
        .uri("/ingredients")
        .body(ingredientMono, Ingredient.class)
        .retrieve()
        .bodyToMono(Ingredient.class);

    result.subscribe();

    Mono<Ingredient> result1 = defaultWebClient.post()
        .uri("/ingredients")
        .bodyValue(new Ingredient("test", "name1", Type.CHEESE))
        .retrieve()
        .bodyToMono(Ingredient.class);

    result1.subscribe();
  }

  public void put() {
    Mono<Void> voidMono = defaultWebClient.put()
        .uri("/ingredients")
        .bodyValue(new Ingredient("test", "name", Type.SAUCE))
        .retrieve()
        .bodyToMono(Void.class);

    voidMono.subscribe();
  }

  public void exceptionHandling() {
    Long ingredientId = 3L;

    Mono<Ingredient> ingredientMono = defaultWebClient.get()
        .uri("/ingredients/{id}", ingredientId)
        .retrieve()
        .onStatus(status -> status.is4xxClientError(),
            response -> Mono.just(new RuntimeException("not found")))
        .bodyToMono(Ingredient.class);

    ingredientMono.subscribe();
  }

  public void usingExchange() {
    Long ingredientId = 3L;

    Mono<Ingredient> ingredientMono = defaultWebClient.get()
        .uri("/ingredients/{id}", ingredientId)
        .exchange()
        .flatMap(cr -> {
          if (!cr.headers().header("Accept-Type").contains("true")) {
            return Mono.empty();
          }
          if (cr.statusCode().is4xxClientError()) {
            return Mono.error(() -> new RuntimeException("not found"));
          }
          return Mono.just(cr);
        })
        .flatMap(cr -> cr.bodyToMono(Ingredient.class));

    ingredientMono.subscribe();
  }

}
