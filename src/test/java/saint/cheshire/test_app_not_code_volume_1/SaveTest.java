package saint.cheshire.test_app_not_code_volume_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import saint.cheshire.test_app_not_code_volume_1.data.entity.VerySimpleEntity;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Файлы для этого теста расположены в директории resources/scenarios/saving
 */
@DisplayName("Тесты сохранения сущности")
public class SaveTest extends ApplicationTest {

    private static final String SAVE_ENDPOINT = "api/v1/save";
    private static final String SUCCESSFUL_REQUEST = loadResourceData("scenarios/saving/successful/request.json");
    private static final String EMPTY_REQUEST = loadResourceData("scenarios/saving/empty_request/request.json");
    private static final String EMPTY_REQUEST_EXPECTED_RESPONSE = loadResourceData("scenarios/saving/empty_request/response.json");
    private static final String NULL_REQUEST_EXPECTED_RESPONSE = loadResourceData("scenarios/saving/null_request/response.json");

    @Test
    @DisplayName("Успешный запрос на сохранение с проверкой строки в таблице")
    public void testSuccessful() {
        UUID expectedId = UUID.fromString("ca7cf5cc-8b02-410c-bf03-292d7c5aca03");
        String expectedCode = "XYZ";

        given()
                .body(SUCCESSFUL_REQUEST)
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_OK);

        VerySimpleEntity expectedEntity = VerySimpleEntity.builder()
                .id(expectedId)
                .code(expectedCode)
                .status("New")
                .build();

        VerySimpleEntity result = verySimpleEntityRepositoryExtension.findByCode(expectedCode);

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("status")
                .isEqualTo(expectedEntity);
    }

    @Test
    @DisplayName("Ошибка при отправке пустого запроса")
    public void testEmptyRequest() {
        String response = given()
                .body(EMPTY_REQUEST)
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .extract().body().asString();

        assertThatJson(response)
                .isEqualTo(EMPTY_REQUEST_EXPECTED_RESPONSE);
    }

    @Test

    @DisplayName("Ошибка при отправке запроса без тела")
    public void testNullRequest() {
        String response = given()
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_BAD_REQUEST)
                .extract().body().asString();

        assertThatJson(response)
                .isEqualTo(NULL_REQUEST_EXPECTED_RESPONSE);
    }

}
