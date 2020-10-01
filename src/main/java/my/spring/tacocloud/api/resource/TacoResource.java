//package my.spring.tacocloud.api.resource;
//
//import lombok.Getter;
//import my.spring.tacocloud.Taco;
//import org.springframework.hateoas.ResourceSupport;
//import org.springframework.hateoas.core.Relation;
//
//import java.util.Date;
//import java.util.List;
//
///* 리소스 클래스의 이름이 바뀌더라도 클라이언트단에서는 이름 변경을 인지할 필요가
//* 없도록 하기 위해, HATEOAS 가 생성하는 링크의 이름을 강제로 지정한다. */
//@Relation(value = "taco", collectionRelation = "tacos")
//@Getter
//public class TacoResource extends ResourceSupport {
//
//    /* Taco 객체 데이터에는 사용된 재료 필드가 있으며, 이 재료 데이터 역시 링크를 포함하여
//    * api 에 응답하기 위해서 ingredientResourceAssembler 를 만들고 이것으로 링크를 생성시킨다. */
//    private static final IngredientResourceAssembler ingredientResourceAssembler = new IngredientResourceAssembler();
//
//    private final String name;
//    private final Date createdAt;
//    private final List<IngredientResource> ingredients;
//
//    public TacoResource(Taco taco) {
//        this.name = taco.getName();
//        this.createdAt = taco.getCreatedAt();
//        this.ingredients = ingredientResourceAssembler.toResources(taco.getIngredients());
//    }
//}
