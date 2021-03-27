package my.spring.tacocloud.reactive.repository;

import my.spring.tacocloud.Taco;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class TacoRepoStub implements TacoReactiveRepo {

  @Override
  public Flux<Taco> findAll() {
    return null;
  }

  @Override
  public <T> T save(T t) {
    return null;
  }
}
