package my.spring.tacocloud.reactive;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import my.spring.tacocloud.Ingredient;
import my.spring.tacocloud.Ingredient.Type;
import my.spring.tacocloud.Taco;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class DesignTacoReactiveControllerTest {

  @Test
  public void shouldReturnRecentTacos() {
    List<Taco> tacos = new ArrayList<>(Arrays.asList(testTaco(1L), testTaco(2L), testTaco(3L),
        testTaco(4L), testTaco(5L), testTaco(6L), testTaco(7L), testTaco(8L)));

    Flux<Taco> tacoFlux = Flux.fromIterable(tacos);

    TacoReactiveRepo tacoRepository = Mockito.mock(TacoReactiveRepo.class);
    when(tacoRepository.findAll()).thenReturn(tacoFlux);
    
    WebTestClient testClient = WebTestClient.bindToController(new DesignTacoReactiveController(tacoRepository))
        .build();

    testClient.get().uri("/webflux/design/recent")
        .exchange()
        .expectStatus().isOk()
        .expectBody()
        .jsonPath("$").isArray()
        .jsonPath("$").isNotEmpty()
        .jsonPath("$[0].name").isEqualTo("Taco 1");

    testClient.get().uri("/webflux/design/recent")
        .accept(MediaType.APPLICATION_JSON)
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(Taco.class)
        .contains(tacos.toArray(new Taco[] {}));
  }

  @Test
  public void saveTacoTest() {
    TacoReactiveRepo tacoReactiveRepo = Mockito.mock(TacoReactiveRepo.class);
    Mono<Taco> unsaved = Mono.just(testTaco(null));
    Taco saved = testTaco(null);
    saved.setId(1L);
    Mono<Taco> savedTacoMono = Mono.just(saved);

    when(tacoReactiveRepo.save(any())).thenReturn(savedTacoMono);

    WebTestClient testClient = WebTestClient.bindToController(new DesignTacoReactiveController(tacoReactiveRepo))
        .build();

    testClient.post()
        .uri("/webflux/design")
        .contentType(MediaType.APPLICATION_JSON)
        .body(unsaved, Taco.class)
        .exchange()
        .expectStatus().isOk()
        .expectBody(Taco.class);

  }

  private Taco testTaco(Long number) {
    Taco taco = new Taco();
    taco.setId(UUID.randomUUID().getMostSignificantBits());
    taco.setName("Taco " + number);
    List<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient("INGA", "Ingredient A", Type.WRAP));
    ingredients.add(new Ingredient("INGB", "Ingredient B", Type.PROTEIN));
    taco.setIngredients(ingredients);

    return taco;
  }

}
