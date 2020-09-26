package my.spring.tacocloud.data.jdbctemplate;

import my.spring.tacocloud.Ingredient;
import my.spring.tacocloud.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Repository
public class JdbcTacoRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient : taco.getIngredients()) {
            saveIngredientToTaco(tacoId, ingredient);
        }

        return taco;
    }

    @SuppressWarnings("SqlResolve")
    private long saveTacoInfo(Taco taco) {
        taco.setCreatedAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory(
                "insert into Taco (name, createdAt) values ( ?,? )",
                Types.VARCHAR,
                Types.TIMESTAMP
        ).newPreparedStatementCreator(Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));

        /*
        * DBMS 에서 제공하는 auto_increment 같은 Bigint 타입의 PK 를 위해서
        * 숫자를 자동으로 관리해주는 객체이다.
        * */
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);

        /*
        * update 트랜젝션 이후에 'DBMS' 에서 자동 생성된 숫자(PK) 를 반환해준다.
        * 자바에서 생성된 숫자를 PK 에 저장한 것이 아니다.
        * */
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @SuppressWarnings("SqlResolve")
    private void saveIngredientToTaco(long tacoId, Ingredient ingredient) {
        jdbcTemplate.update(
                "insert into Taco_Ingredients (taco, ingredient) values ( ?,? )",
                tacoId,
                ingredient.getId()
        );
    }
}
