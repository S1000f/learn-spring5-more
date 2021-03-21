package my.spring.tacocloud;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class ReactorTest {

  @Test
  public void createFlux() {
    // 리액티브 타입 생성
    Flux<String> fruitFlux = Flux.just("Apple", "Banana", "Orange");
    // 구독. 구독한다는 것은 파이프라인을 생성한다는 의미
    fruitFlux.subscribe(f -> System.out.println("here's some fruit: " + f));

    StepVerifier.create(fruitFlux)
        .expectNext("Apple")
        .expectNext("Banana")
        .expectNext("Orange")
        .verifyComplete();

    String[] fruits = new String[] {"Apple", "Banana", "Orange"};
    Flux<String> flux1 = Flux.fromArray(fruits);

    StepVerifier.create(flux1)
        .expectNext("Apple")
        .expectNext("Banana")
        .expectNext("Orange")
        .verifyComplete();

    List<Integer> list = new ArrayList<>(Arrays.asList(1, 2));
    Flux<Integer> flux2 = Flux.fromIterable(list);

    StepVerifier.create(flux2)
        .expectNext(1)
        .expectNext(2)
        .verifyComplete();

    Flux<Integer> flux3 = Flux.fromStream(list.stream().map(i -> i + 1));

    StepVerifier.create(flux3)
        .expectNext(2)
        .expectNext(3)
        .verifyComplete();

  }

}
