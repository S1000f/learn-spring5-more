package my.spring.tacocloud.integrations;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.file.FileHeaders;
import org.springframework.messaging.handler.annotation.Header;

/* 메시지 게이트웨이 인터페이스 */
// 이 어노테이션이 붙은 인터페이스를 기반으로 스프링이 런타임시에 구현체를 자동 생성한다
// Spring Data JPA 가 JpaRepository 인터페이스 구현체를 자동으로 생성해주는 것과 유사하다
@MessagingGateway(defaultRequestChannel = "textInChannel") // 이 구현체에서 생성된 메시지들은 textInChannel 로 전송된다
public interface FileWriterGateway {

    // @Header 는 파일의 페이로드가 아니라 파일이름이 될것임을 알려준다
    void writeToFile(@Header(FileHeaders.FILENAME) String filename, String data);
}
