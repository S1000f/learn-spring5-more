package my.spring.tacocloud.data.jdbctemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import my.spring.tacocloud.Order;
import my.spring.tacocloud.Taco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcOrderRepository {

    private final SimpleJdbcInsert orderInserter;
    private final SimpleJdbcInsert orderTacoInserter;
    private final ObjectMapper mapper;

    @Autowired
    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order")
                .usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Taco_Order_Tacos");
        this.mapper = new ObjectMapper();
    }

    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();

        for (Taco taco : tacos) {
            saveTacoOrder(taco, orderId);
        }

        return order;
    }

    private long saveOrderDetails(Order order) {
        /*
        * 원래 잭슨 mapper 는 json 을 자바객체로, 혹은 자바를 json 으로 변환하는데 사용되지만
        * 여기서는 자바객체(Order) 를 자바객체(Map) 로 변환하는데 사용한 특이한 케이스이다.
        * */
        @SuppressWarnings("unchecked")
        Map<String, Object> values = mapper.convertValue(order, Map.class);
        /*
        * 잭슨 mapper 는 Date 타입을 long 타입으로 변환시키는데,
        * DBMS 에 날짜를 저장할때는 long 타입으로 저장할 수 없으므로
        * 잭슨으로 변환된 long 타입 날짜를 다시 Date 타입으로 바꿔주는 작업이 필요하다.
        * */
        values.put("placedAt", order.getPlacedAt());

        return orderInserter.executeAndReturnKey(values)
                .longValue();
    }

    private void saveTacoOrder(Taco taco, long orderId) {
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
