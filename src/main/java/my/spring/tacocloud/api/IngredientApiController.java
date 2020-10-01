package my.spring.tacocloud.api;

import my.spring.tacocloud.Ingredient;
import my.spring.tacocloud.data.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(path="/ingredients", produces="application/json")
@CrossOrigin(origins="*")
@RestController
public class IngredientApiController {

    private final IngredientRepository repo;

    @Autowired
    public IngredientApiController(IngredientRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return repo.findAll();
    }

}
