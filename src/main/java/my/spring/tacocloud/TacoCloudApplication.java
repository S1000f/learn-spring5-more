package my.spring.tacocloud;

import my.spring.tacocloud.data.IngredientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class TacoCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
    }

    /* testprofile 프로파일이 아닐때만 생성된다.
    * 즉, 프로파일이 testprofile 로 변경되면 이 빈 객체는 생성되지 않는다. */
    @Profile("!testprofile")
    /* Spring Data JPA 을 사용하는 애플리케이션이 실행될때(혹은 최초 배포시) 데이터베이스에 기본적으로 구비되어
    * 있어야만 하는 데이터를 저장시키는 오퍼레이션이다.
    * Spring Data 를 사용하지 않는다면, resources 폴더 밑의 data.sql 안에 내용을 작성해두면 된다. */
    @Bean
    public CommandLineRunner dataLoader(IngredientRepository repository) {
        return args -> {
            repository.save(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
            repository.save(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
            repository.save(new Ingredient("GRBF", "Ground Beef", Ingredient.Type.PROTEIN));
            repository.save(new Ingredient("CARN", "Carnitas", Ingredient.Type.PROTEIN));
            repository.save(new Ingredient("TMTO", "Diced Tomatoes", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("LETC", "Lettuce", Ingredient.Type.VEGGIES));
            repository.save(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("JACK", "Monterrey Jack", Ingredient.Type.CHEESE));
            repository.save(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
            repository.save(new Ingredient("SRCR", "Sour Cream", Ingredient.Type.SAUCE));
        };
    }

}
