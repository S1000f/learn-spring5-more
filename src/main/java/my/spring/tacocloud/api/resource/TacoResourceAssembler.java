//package my.spring.tacocloud.api.resource;
//
//import my.spring.tacocloud.Taco;
//import my.spring.tacocloud.api.DesignTacoApiController;
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
//
//public class TacoResourceAssembler extends ResourceAssemblerSupport<Taco, TacoResource> {
//
//    public TacoResourceAssembler() {
//        /* 링크에 사용될 기본 URI 경로를 얻기위해 DesignTacoApiController 를 인자로 주입한다. */
//        super(DesignTacoApiController.class, TacoResource.class);
//    }
//
//    /* 만약 TacoResource 클래스가 기본생성자를 가지고 있다면 생략 가능하다. */
//    @Override
//    protected TacoResource instantiateResource(Taco taco) {
//        return new TacoResource(taco);
//    }
//
//    /* taco.getId() 의 값으로 self link 을 생성한다.
//    * 두번째 매개변수 taco 객체는 위의 instantiateResource(Taco taco) 에 전달되는 것이다.
//    * */
//    @Override
//    public TacoResource toResource(Taco taco) {
//        return createResourceWithId(taco.getId(), taco);
//    }
//
//}
