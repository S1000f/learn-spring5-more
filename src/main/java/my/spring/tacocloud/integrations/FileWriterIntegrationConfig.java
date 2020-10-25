package my.spring.tacocloud.integrations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.file.FileWritingMessageHandler;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.support.FileExistsMode;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.File;

@Configuration
public class FileWriterIntegrationConfig {

    // 변환기
    @Bean
    @Transformer(inputChannel = "textInChannel", outputChannel = "fileWriterChannel")
    public GenericTransformer<String, String> upperCaseTransform() {
        return text -> text.toUpperCase();
    }

    // 서비스 액티베이터
    @Bean
    @ServiceActivator(inputChannel = "fileWriterChannel")
    public FileWritingMessageHandler fileWriter() {
        FileWritingMessageHandler handler = new FileWritingMessageHandler(new File("/tmp/taco/files"));
        handler.setExpectReply(false);
        handler.setFileExistsMode(FileExistsMode.APPEND);
        handler.setAppendNewLine(true);

        return handler;
    }

    // DSL 구성방법. 위의 두 개의 빈과 같은것이다. 이 방법을 사용하려면 @Bean 을 붙이면 된다
    public IntegrationFlow fileWriterFlow() {
        return IntegrationFlows.from(MessageChannels.direct("textInChannel")) // inputChannel 지정
                .<String, String>transform(text -> text.toUpperCase()) // 변환기
                .handle(Files.outboundAdapter(new File("/tmp/taco/files")) // 서비스 액티베이터
                        .fileExistsMode(FileExistsMode.APPEND)
                        .appendNewLine(true)
                )
                .get();
    }

}
