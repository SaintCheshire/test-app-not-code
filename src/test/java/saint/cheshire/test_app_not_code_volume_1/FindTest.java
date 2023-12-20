package saint.cheshire.test_app_not_code_volume_1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;

@DisplayName("Тесты поиска сущности")
public class FindTest extends ApplicationTest {

    private static final String SAVE_ENDPOINT = "api/v1/save";
    private static final String FIND_ENDPOINT = "api/v1/find";
    private static final String SAVE_REQUEST = loadResourceData("scenarios/finding/successful/save_request.json");
    private static final String FIND_REQUEST = loadResourceData("scenarios/finding/successful/find_request.json");
    private static final String EXPECTED_FIND_RESPONSE = loadResourceData("scenarios/finding/successful/find_response.json");

    private static final String NOT_FOUND_FIND_REQUEST = loadResourceData("scenarios/finding/not_found/find_request.json");

    @Test
    @DisplayName("Успешный поиск сущности после её сохранения")
    public void testFinding() {
        given()
                .body(SAVE_REQUEST)
                .post(SAVE_ENDPOINT)
                .then()
                .statusCode(SC_OK);

        String response = given()
                .body(FIND_REQUEST)
                .post(FIND_ENDPOINT)
                .then()
                .statusCode(SC_OK)
                .extract().body().asString();

        assertThatJson(response)
                .isEqualTo(EXPECTED_FIND_RESPONSE);
    }

    @Test
    @DisplayName("Пустой ответ при попытке поиска несуществующей сущности")
    public void testNotFound() {
        given()
                .body(NOT_FOUND_FIND_REQUEST)
                .post(FIND_ENDPOINT)
                .then()
                .statusCode(SC_NOT_FOUND)
                .extract().body().asString();
    }

}
