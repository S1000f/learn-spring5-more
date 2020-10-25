package my.spring.tacocloud.integrations.http;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RequestMapping("/")
@RestController
public class HttpListener {

    private final HttpGateway httpGateway;

    @Autowired
    public HttpListener(HttpGateway httpGateway) {
        this.httpGateway = httpGateway;
    }

    @RequestMapping
    public ResponseEntity<?> run(HttpHeaders httpHeaders, RequestEntity<?> requestEntity) {
        return httpGateway.flow(httpHeaders, requestEntity);
    }
}
