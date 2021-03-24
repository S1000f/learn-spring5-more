package my.spring.tacocloud.functionrouter;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.net.URI;
import lombok.RequiredArgsConstructor;
import my.spring.tacocloud.Taco;
import my.spring.tacocloud.data.TacoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class RouterFunctionConfig {

  private final TacoRepository tacoRepository;

  @Bean
  public RouterFunction<?> helloRouterFunction() {
    return route(GET("/hello"),
        request -> ok().body(Mono.just("Hello world!"), String.class))
        .andRoute(GET("/bye"),
            request -> ok().body(Mono.just("See ya!"), String.class));
  }

  @Bean
  public RouterFunction<?> tacoDesignRouter() {
    return route(GET("/design/taco"), this::recent)
        .andRoute(POST("/design"), request -> postTaco(request));
  }

  public Mono<ServerResponse> recent(ServerRequest request) {
    return ServerResponse.ok()
        .body(Flux.fromIterable(tacoRepository.findAll()).take(12), Taco.class);
  }

  public Mono<ServerResponse> postTaco(ServerRequest request) {
    Mono<Taco> taco = request.bodyToMono(Taco.class);
    return ServerResponse.created(URI.create("http://localhost:8080/design/taco/" + taco.map(Taco::getId)))
        .body(taco, Taco.class);
  }

}
