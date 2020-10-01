package my.spring.tacocloud.api;

//import my.spring.tacocloud.api.resource.TacoResource;
//import my.spring.tacocloud.api.resource.TacoResourceAssembler;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.hateoas.Resources;
//import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import my.spring.tacocloud.Taco;
import my.spring.tacocloud.data.TacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/* CORS(cross-origin resource sharing 헤더를 응답에 자동으로 붙여준다. */
@CrossOrigin(origins = "*")
/* 요청 헤더의 Accept 요소 중 어떤 content-type 에만 응답할 것인지 지정한다.
* 여러개를 지정 할 수도 있다. 만약 요청 헤더의 Accept 값이 *//* 인 경우, 이 설정과 무관하게
* 응답을 받을 수 있다. */
@RequestMapping(path = "/design", produces = {"application/json", "text/html"})
@RestController
public class DesignTacoApiController {

    private final TacoRepository tacoRepo;

    @Autowired
    public DesignTacoApiController(TacoRepository repository) {
        this.tacoRepo = repository;
    }

    /* HATEOAS 링크를 추가하여 응답한다. 아래는 0.25.0 버전 기준이며,
    현재 최신버전은 1.0.4 이다. 이전버전과 변경점이 있어 그대로 사용할 수 없다.
    **/
//    @GetMapping("/recent")
//    public Resources<TacoResource> recentTacos() {
//        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt")
//                .descending());
//        List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//        /* TacoResourceAssembler 에서 toResource 를 미리 구현해놨으므로, 리소스객체의 배열을
//        반환하는 toResources() 메소드를 바로 사용할 수 있다. */
//        List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
//        /* 자바의 List, Map 등 컬렉션을 인자를 전달하며 초기화 할 수 있는것처럼,
//        생성시 Iterable 이 구현된 위의 객체를 인자로 넣어서 초기화 할 수 있다. */
//        Resources<TacoResource> recentTacoResources = new Resources<>(tacoResources);
//        recentTacoResources.add(
//                ControllerLinkBuilder.linkTo(DesignTacoApiController.class)
//                .slash("recent")
//                .withRel("recents")
//        );
//
//        return recentTacoResources;
//    }

    /* {id} 는 placeholder 변수를 선언하는 것으로, @PathVariable 가 붙은 매개변수에 해당 값이 할당된다. */
    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoByIdResponse(@PathVariable("id") Long id) {
        Optional<Taco> result = tacoRepo.findById(id);
        /* 반환값이 null 이더라도 200 상태를 응답하므로, 보내줄 값이 null 인 경우엔
        * 200이 아니라 404 에러를 응답하도록 하는것이 좋다(필수는 아님) */
        return result.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
                .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    /* POST 요청은 응답으로 별도의 데이터를 전송해줄 필요가 없다. 따라서 GET 요청의 응답처럼
    ResponseEntity<> 의 반환을 생략했으므로, 이 요청이 제대로 수행되면 항상 200(OK) 상태코드가
    응답된다. 하지만 항상 더욱 정확한 응답상태를 보내주는 것이 좋으므로, @ResponseStatus 을 지정하여
    요청의 결과로 리소스가 생성(created)되면 200이 아니라 201(CREATED)을 응답한다. */
    @ResponseStatus(HttpStatus.CREATED)
    /* consumes 에 지정된 Content-type 요청만 처리한다. 클래스 레벨의 produces 속성값 보다 우선한다. */
    @PostMapping(consumes = "application/json")
    /* @RequestBody 가 붙으면, 요청 body 의 JSON 데이터를 해당 자바객체로 자동 맵핑해준다.
    * 이 어노테이션이 없으면 기존의 Spring MVC 의 모델객체로 인식되므로 주의. */
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }


}
