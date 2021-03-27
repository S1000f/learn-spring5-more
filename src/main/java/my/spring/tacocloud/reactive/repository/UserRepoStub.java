package my.spring.tacocloud.reactive.repository;

import my.spring.tacocloud.User;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class UserRepoStub implements UserReactiveRepo {

  @Override
  public Mono<User> findByUsername(String username) {
    return null;
  }
}
