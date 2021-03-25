package my.spring.tacocloud.reactive;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Taco;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/webflux/design")
@RestController
public class DesignTacoReactiveController {

  private final TacoReactiveRepo tacoRepository;

  @GetMapping("/recent")
  public Flux<Taco> recentTacos() {
    return tacoRepository.findAll().take(12);
  }

  @PostMapping
  public Mono<Taco> saveTaco(Taco taco) {
    return Mono.just(taco);
  }

}
