package saint.cheshire.test_app_not_code;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import saint.cheshire.test_app_not_code.data.entity.VerySimpleEntity;

import java.io.File;
import java.util.UUID;

import static java.util.UUID.fromString;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Файлы для этого теста расположены в директории resources/scenarios/saving
 */
public class SaveTest extends ApplicationTest {

    private static final String SAVE_ENDPOINT = "api/v1/save";
    private static final UUID ID = fromString("ca7cf5cc-8b02-410c-bf03-292d7c5aca03");

    @Test
    public void testSaving() {
        RestAssured.given()
                .body(new File("src/test/resources/scenarios/saving/save_request.json"))
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_OK);

        VerySimpleEntity expected = VerySimpleEntity.builder()
                .id(ID)
                .code("XYZ")
                .status("New")
                .build();

        VerySimpleEntity result = verySimpleEntityRepository.findById(ID).get();

        assertThat(result).isEqualTo(expected);
    }

}
