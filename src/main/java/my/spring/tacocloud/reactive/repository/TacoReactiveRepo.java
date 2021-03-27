package my.spring.tacocloud.reactive.repository;

import my.spring.tacocloud.Taco;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TacoReactiveRepo {

  Flux<Taco> findAll();

  <T> T save(T t);
}
