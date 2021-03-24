package my.spring.tacocloud.reactive;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import my.spring.tacocloud.Ingredient;
import my.spring.tacocloud.Ingredient.Type;
import my.spring.tacocloud.Taco;
import my.spring.tacocloud.data.TacoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;

public class DesignTacoReactiveControllerTest {

  @Test
  public void shouldReturnRecentTacos() {
    List<Taco> tacos = new ArrayList<>(Arrays.asList(testTaco(1L), testTaco(2L), testTaco(3L),
        testTaco(4L), testTaco(5L), testTaco(6L), testTaco(7L), testTaco(8L)));

    Flux<Taco> tacoFlux = Flux.fromIterable(tacos);

    TacoReactiveRepo tacoRepository = Mockito.mock(TacoReactiveRepo.class);
    when(tacoRepository.findAll()).thenReturn(tacoFlux);
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
