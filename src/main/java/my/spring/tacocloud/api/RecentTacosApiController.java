package my.spring.tacocloud.api;

import my.spring.tacocloud.Taco;
import my.spring.tacocloud.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/* Spring Data REST 사용시 REST 리소스와 Api 엔드포인트가 자동 생성된다.
* @RepositoryRestController 는 이 클래스의 기본 맵핑 경로를 Spring Data REST 의 기본경로와 일치시킨다.
* */
@RepositoryRestController
public class RecentTacosApiController {

    private final TacoRepository tacoRepo;

    @Autowired
    public RecentTacosApiController(TacoRepository tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    /* RepositoryRestController 의 메소드 반환값은 자동으로 응답 바디에 맵핑되지 않으므로,
    * @ResponseBody 어노테이션을 붙이거나, ResponseEntity 를 반환하도록 해야한다. */
    @ResponseBody
    /* RepositoryRestController 이므로 경로는 .../api/tacos/latest/ 로 맵핑된다. */
    @GetMapping(path = "/tacos/latest", produces = "application/hal+json")
    public Taco latestTaco() {
        PageRequest pageRequest = PageRequest.of(0, 1, Sort.by("createdAt").descending());
        List<Taco> result = tacoRepo.findAll(pageRequest).getContent();

        return result.isEmpty() ? null : result.get(0);
    }

}
