package my.spring.tacocloud.web;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

/* 구성속성 파일에서 읽어온 값 들을 관리하는 역할을 하는 클래스
* 필수적인 것은 아니며, 값을 사용하는 클래스에서 직접 구성속성 파일에 접근할 수 도 있음
* */
@Data
@Validated
/* resources 에 있는 구성 속성 파일에서 값을 찾아올때 사용될 접두어를 지정한다 */
@ConfigurationProperties(prefix = "taco.orders")
@Component
public class OrderProps {

    /*
    * 찾는 값은 접두어 + 필드명에서 찾아온다.
    * taco.orders.pageSize
    * */
    @Range(min = 0, max = 25, message = "must be between 5 and 25")
    private int pageSize = 20;
}
