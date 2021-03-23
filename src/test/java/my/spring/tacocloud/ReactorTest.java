package my.spring.tacocloud;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;
import reactor.util.function.Tuple2;

@Slf4j
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

    String[] fruits = new String[]{"Apple", "Banana", "Orange"};
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

    Flux<Integer> interval = Flux.range(1, 3);

    StepVerifier.create(interval)
        .expectNext(1)
        .expectNext(2)
        .expectNext(3)
        .verifyComplete();

    Flux<Long> intervalFlux = Flux.interval(Duration.ofSeconds(1L))
        .take(3L);

    StepVerifier.create(intervalFlux)
        .expectNext(0L)
        .expectNext(1L)
        .expectNext(2L)
        .verifyComplete();
  }

  @Test
  public void combination() {
    Flux<String> characters = Flux.just("Garfield", "Kojak", "Barbossa")
        .delayElements(Duration.ofMillis(300));

    Flux<String> foods = Flux.just("Lasagna", "Lollipops", "Apples")
        .delaySubscription(Duration.ofMillis(200))
        .delayElements(Duration.ofMillis(300));

    Flux<String> mergedFlux = characters.mergeWith(foods);
    mergedFlux.subscribe(log::info);

    StepVerifier.create(mergedFlux)
        .expectNext("Garfield")
        .expectNext("Lasagna")
        .expectNext("Kojak")
        .expectNext("Lollipops")
        .expectNext("Barbossa")
        .expectNext("Apples")
        .verifyComplete();
  }

  @Test
  public void zip() {
    Flux<String> characters = Flux.just("Garfield", "Kojak", "Barbossa");
    Flux<String> foods = Flux.just("Lasagna", "Lollipops", "Apples");

    Flux<Tuple2<String, String>> zipped = Flux.zip(characters, foods);

    StepVerifier.create(zipped)
        .expectNextMatches(p -> p.getT1().equals("Garfield") && p.getT2().equals("Lasagna"))
        .expectNextMatches(p -> p.getT1().equals("Kojak") && p.getT2().equals("Lollipops"))
        .expectNextMatches(p -> p.getT1().equals("Barbossa") && p.getT2().equals("Apples"))
        .verifyComplete();

    Flux<String> zipped2 = Flux.zip(characters, foods, (c, f) -> c + " eats " + f);

    StepVerifier.create(zipped2)
        .expectNext("Garfield eats Lasagna")
        .expectNext("Kojak eats Lollipops")
        .expectNext("Barbossa eats Apples")
        .verifyComplete();
  }

  @Test
  public void first() {
    Flux<String> slow = Flux.just("1", "2")
        .delaySubscription(Duration.ofMillis(300));

    Flux<String> fast = Flux.just("0.1", "0.2");

    Flux<String> firstFlux = Flux.first(slow, fast);

    StepVerifier.create(firstFlux)
        .expectNext("0.1")
        .expectNext("0.2")
        .verifyComplete();
  }

  @Test
  public void filtering() {
    Flux<String> skipFlux = Flux.just("one", "two", "skip", "ninety-nine", "one hundred")
        .skip(3);

    StepVerifier.create(skipFlux)
        .expectNext("ninety-nine", "one hundred")
        .verifyComplete();

    Flux<String> skipByDuration = Flux.just("one", "two", "skip", "ninety-nine", "one hundred")
        .delayElements(Duration.ofSeconds(1))
        .skip(Duration.ofSeconds(4));

    StepVerifier.create(skipByDuration)
        .expectNext("ninety-nine", "one hundred")
        .verifyComplete();

    Flux<String> takeFlux = Flux.just("one", "two", "skip", "ninety-nine", "one hundred")
        .take(2);

    StepVerifier.create(takeFlux)
        .expectNext("one")
        .expectNext("two")
        .verifyComplete();

    Flux<String> nationalPark = Flux.just("Yellowstone", "Yosemite", "Grand Canyon")
        .filter(np -> !np.contains(" "));

    StepVerifier.create(nationalPark)
        .expectNext("Yellowstone", "Yosemite")
        .verifyComplete();

    Flux<String> animalFlux = Flux.just("dog", "cat", "dog", "bird")
        .distinct();
    StepVerifier.create(animalFlux)
        .expectNext("dog", "cat", "bird")
        .verifyComplete();
  }

  @Test
  public void mapping() {
    Flux<Integer> stringNum = Flux.just("1", "2", "11")
        .map(Integer::valueOf);

    StepVerifier.create(stringNum)
        .expectNext(1, 2, 11)
        .verifyComplete();

    Flux<Integer> integerFlux = Flux.just("1", "2", "33")
        .flatMap(n -> Mono.just(n)
            .map(Integer::valueOf)
            .subscribeOn(Schedulers.parallel()));

    StepVerifier.create(integerFlux)
        .expectNextMatches(i -> i > 0 && i <= 33)
        .expectNextMatches(i -> i > 0 && i <= 33)
        .expectNextMatches(i -> i > 0 && i <= 33)
        .verifyComplete();
  }

  @Test
  public void buffering() {
    Flux<String> fruitFlux = Flux.just("apple", "banana", "kiwi", "strawberry");

    Flux<List<String>> bufferedFlux = fruitFlux.buffer(3);

    StepVerifier.create(bufferedFlux)
        .expectNext(Arrays.asList("apple", "banana", "kiwi"))
        .expectNext(Arrays.asList("strawberry"))
        .verifyComplete();

    Flux.just("apple", "orange", "banana", "kiwi", "melon")
        .buffer(3)
        .flatMap(x -> Flux.fromIterable(x)
            .map(String::toUpperCase)
            .subscribeOn(Schedulers.parallel())
            .log())
        .subscribe();

    Flux<String> stringFlux = Flux.just("a", "b", "c");
    Mono<List<String>> mono = stringFlux.collectList();

    StepVerifier.create(mono)
        .expectNext(Arrays.asList("a", "b", "c"))
        .verifyComplete();

    Flux<String> stringFlux1 = Flux.just("A", "B", "C");
    Mono<Map<Integer, String>> mapMono = stringFlux1.collectMap(e -> (int) e.charAt(0));

    StepVerifier.create(mapMono)
        .expectNextMatches(map -> map.size() == 3 &&
            map.get(65).equals("A") &&
            map.get(66).equals("B") &&
            map.get(67).equals("C"))
        .verifyComplete();
  }

  @Test
  public void predicating() {
    Flux<String> stringFlux = Flux.just("abc", "ade", "alpine");

    Mono<Boolean> hasAMono = stringFlux.all(s -> s.contains("a"));
    StepVerifier.create(hasAMono)
        .expectNext(true)
        .verifyComplete();

    Mono<Boolean> anyMono = stringFlux.any(s -> s.contains("e"));
    StepVerifier.create(anyMono)
        .expectNext(true)
        .verifyComplete();
  }

}
