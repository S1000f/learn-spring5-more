//package my.spring.tacocloud.api.resource;
//
//import my.spring.tacocloud.Ingredient;
//import my.spring.tacocloud.api.IngredientApiController;
//import org.springframework.hateoas.mvc.ResourceAssemblerSupport;
//
//public class IngredientResourceAssembler extends ResourceAssemblerSupport<Ingredient, IngredientResource> {
//
//    public IngredientResourceAssembler() {
//        super(IngredientApiController.class, IngredientResource.class);
//    }
//
//    @Override
//    protected IngredientResource instantiateResource(Ingredient ingredient) {
//        return new IngredientResource(ingredient);
//    }
//
//    @Override
//    public IngredientResource toResource(Ingredient ingredient) {
//        return createResourceWithId(ingredient.getId(), ingredient);
//    }
//}
