package my.spring.tacocloud.reactive.repository;

import my.spring.tacocloud.User;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UserReactiveRepo {

  Mono<User> findByUsername(String username);

}
