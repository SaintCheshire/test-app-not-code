package saint.cheshire.test_app_not_code_volume_1;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import saint.cheshire.test_app_not_code_volume_1.data.repository.VerySimpleEntityRepository;

import static io.restassured.http.ContentType.JSON;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest {

    @LocalServerPort
    public int applicationPort;

    @Autowired
    VerySimpleEntityRepository verySimpleEntityRepository;

    @BeforeEach
    public void beforeAll() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setPort(applicationPort)
                .setContentType(JSON)
                .build();
    }

}