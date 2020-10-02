package my.spring.tacocloud.restclient;

import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Ingredient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Slf4j
public class RestTemplateClient {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Traverson traverson = new Traverson(URI.create("http://localhost:8080/api"), MediaTypes.HAL_JSON);

    public Ingredient getIngredientById1(String ingredientId) {
        return restTemplate.getForObject(
                "http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                ingredientId
        );
    }

    public Ingredient getIngredientById2(String ingredientId) {
        Map<String, String> uriVar = new HashMap<>();
        uriVar.put("id", ingredientId);

        return restTemplate.getForObject(
                "http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                uriVar
        );
    }

    public Ingredient getIngredientById3(String ingredientId) {
        Map<String, String> uriVar = new HashMap<>();
        uriVar.put("id", ingredientId);
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/ingredients/{id}")
                .build(uriVar);

        return restTemplate.getForObject(uri, Ingredient.class);
    }

    public Ingredient getIngredientById4(String ingredientId) {
        ResponseEntity<Ingredient> responseEntity = restTemplate.getForEntity(
                "http://localhost:8080/ingredients/{id}",
                Ingredient.class,
                ingredientId
        );
        log.info("Fetched time : " + responseEntity.getHeaders().getDate());

        return responseEntity.getBody();
    }

    public void putIngredient(Ingredient ingredient) {
        restTemplate.put(
                "http://localhost:8080/ingredients/{id}",
                ingredient,
                ingredient.getId()
        );
    }

    public void deleteIngredient(Ingredient ingredient) {
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }

    public Ingredient postIngredient(Ingredient ingredient) {
        return restTemplate.postForObject(
                "http://localhost:8080/ingredients/",
                ingredient,
                Ingredient.class
        );
    }

    /* POST 결과로 생성된 리소스 위치를 반환받는다. */
    public URI postIngredientandGetURL(Ingredient ingredient) {
        return restTemplate.postForLocation("http://localhost:8080/ingredients", ingredient);
    }

    public Ingredient postIngredientAndGet(Ingredient ingredient) {
        ResponseEntity<Ingredient> responseEntity = restTemplate.postForEntity(
                "http://localhost:8080/ingredients/",
                ingredient,
                Ingredient.class
        );
        log.info("new resource created at : " + responseEntity.getHeaders().getLocation());

        return responseEntity.getBody();
    }

    public List<Ingredient> getIngredientsWithTraverson() {
        ParameterizedTypeReference<CollectionModel<Ingredient>> ingredientType =
                new ParameterizedTypeReference<CollectionModel<Ingredient>>() {};

        CollectionModel<Ingredient> resource = traverson.follow("ingredients")
                .toObject(ingredientType);

        Collection<Ingredient> ingredients;
        if (resource != null) {
            ingredients = resource.getContent();
            return new ArrayList<>(ingredients);
        }

        return null;
    }

    /* Traverson 으로 원하는 api 의 엔드포인트까지 이동한다음
     해당 엔드포인트의 HAL 링크를 통해 URI 를 얻는다.
      알게된 URI 를 이용하여 RestTemplate 로 post 메소드를 수행한다. */
    public Ingredient addIngredient(Ingredient ingredient) {
        String uri = traverson.follow("ingredients")
                .asLink()
                .getHref();

        return restTemplate.postForObject(uri, ingredient, Ingredient.class);
    }


}
