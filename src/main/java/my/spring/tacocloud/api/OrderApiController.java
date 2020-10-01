package my.spring.tacocloud.api;

import lombok.extern.slf4j.Slf4j;
import my.spring.tacocloud.Order;
import my.spring.tacocloud.data.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
/* CORS(cross-origin resource sharing 헤더를 응답에 자동으로 붙여준다. */
@CrossOrigin(origins = "*")
/* 요청 헤더의 Accept 요소 중 어떤 content-type 에만 응답할 것인지 지정한다.
 * 여러개를 지정 할 수도 있다. 만약 요청 헤더의 Accept 값이 *//* 인 경우, 이 설정과 무관하게
 * 응답을 받을 수 있다. */
@RequestMapping(path = "/orders", produces = "application/json")
@RestController
public class OrderApiController {

    private final OrderRepository orderRepo;

    @Autowired
    public OrderApiController(OrderRepository orderRepo) {
        this.orderRepo = orderRepo;
    }

    @ResponseStatus(HttpStatus.CREATED)
    /* PUT 은 서버의 데이터 전체를 변경할 때 사용되고, PATCH 는 데이터의 일부분을 변경할 때
    * 사용되는 메소드이다. order 의 일부분만 변경하려는 목적에서는 @PatchMapping 을 사용하는게 좋다. */
    @PatchMapping(path = "/{orderId}", consumes = "application/json")
    public Order patchOrder(@PathVariable("orderId") Long orderId, @RequestBody Order patch) {
        Optional<Order> result = orderRepo.findById(orderId);
        if (result.isPresent()) {
            Order order = result.get();
            if (patch.getDeliveryName() != null) {
                order.setDeliveryName(patch.getDeliveryName());
            }
            if (patch.getDeliveryStreet() != null) {
                order.setDeliveryStreet(patch.getDeliveryStreet());
            }
            if (patch.getDeliveryCity() != null) {
                order.setDeliveryCity(patch.getDeliveryCity());
            }
            if (patch.getDeliveryState() != null) {
                order.setDeliveryState(patch.getDeliveryState());
            }
            if (patch.getDeliveryZip() != null) {
                order.setDeliveryZip(patch.getDeliveryState());
            }
            if (patch.getCcNumber() != null) {
                order.setCcNumber(patch.getCcNumber());
            }
            if (patch.getCcExpiration() != null) {
                order.setCcExpiration(patch.getCcExpiration());
            }
            if (patch.getCcCVV() != null) {
                order.setCcCVV(patch.getCcCVV());
            }
            return orderRepo.save(order);
        }

        return null;
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Order> deleteOrder(@PathVariable("orderId") Long orderId) {
        try {
            orderRepo.deleteById(orderId);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
    }
}
