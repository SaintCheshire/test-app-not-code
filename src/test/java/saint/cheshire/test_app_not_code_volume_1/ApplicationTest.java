package saint.cheshire.test_app_not_code_volume_1;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ActiveProfiles;
import saint.cheshire.test_app_not_code_volume_1.repository.VerySimpleEntityRepositoryExtension;

import static io.restassured.http.ContentType.JSON;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest {

    @LocalServerPort
    public int applicationPort;

    @Autowired
    VerySimpleEntityRepositoryExtension verySimpleEntityRepositoryExtension;

    @BeforeEach
    public void beforeAll() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(applicationPort)
                .setContentType(JSON)
                .setAccept(JSON)
                .build();
    }
    @AfterEach
    public void afterEach() {
        verySimpleEntityRepositoryExtension.deleteAll();
    }

    @SneakyThrows
    public static String loadResourceData(String resourceFile) {
        return IOUtils.toString(new ClassPathResource(resourceFile).getInputStream(), UTF_8.displayName());
    }

}