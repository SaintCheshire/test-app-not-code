package saint.cheshire.test_app_not_code_volume_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import saint.cheshire.test_app_not_code_volume_1.data.entity.VerySimpleEntity;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_OK;
import static saint.cheshire.test_app_not_code_volume_1.Endpoints.SAVE_ENDPOINT;

/**
 * Файлы для этого теста расположены в директории resources/scenarios/saving
 */
@DisplayName("Тесты сохранения сущности")
public class SaveTest extends ApplicationTest {

    private static final String SUCCESSFUL_REQUEST = loadResourceData("feature/saving/successful/request.json");
    private static final String EMPTY_REQUEST = loadResourceData("feature/saving/empty_request/request.json");
    private static final String EMPTY_REQUEST_EXPECTED_RESPONSE = loadResourceData("feature/saving/empty_request/response.json");
    private static final String NULL_REQUEST_EXPECTED_RESPONSE = loadResourceData("feature/saving/null_request/response.json");
    private static final String VERY_SIMPLE_ENTITY = loadResourceData("feature/finding/successful/very_simple_entity.json");

    @Test
    @DisplayName("Успешный запрос на сохранение с проверкой строки в таблице")
    public void testSuccessful() {
        String expectedCode = "XYZ";

        given()
                .body(SUCCESSFUL_REQUEST)
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_OK);

        VerySimpleEntity result = verySimpleEntityRepositoryExtension.findByCode(expectedCode);

        assertThatJson(result).isEqualTo(VERY_SIMPLE_ENTITY);
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
