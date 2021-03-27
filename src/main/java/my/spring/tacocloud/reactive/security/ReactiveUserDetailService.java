package my.spring.tacocloud.reactive.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.reactive.repository.UserReactiveRepo;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReactiveUserDetailService implements ReactiveUserDetailsService {

  private final UserReactiveRepo userReactiveRepo;

  @Override
  public Mono<UserDetails> findByUsername(String username) {
    return userReactiveRepo.findByUsername(username)
        .flatMap(user -> Mono.just((UserDetails) user));
  }
}
