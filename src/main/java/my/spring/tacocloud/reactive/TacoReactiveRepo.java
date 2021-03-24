package my.spring.tacocloud.reactive;

import my.spring.tacocloud.Taco;
import reactor.core.publisher.Flux;

public interface TacoReactiveRepo {

  Flux<Taco> findAll();
}
