package saint.cheshire.test_app_not_code_volume_1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.apache.http.HttpStatus.SC_OK;
import static saint.cheshire.test_app_not_code_volume_1.Endpoints.FIND_ENDPOINT;
import static saint.cheshire.test_app_not_code_volume_1.Endpoints.SAVE_ENDPOINT;

@DisplayName("Тесты поиска сущности")
public class FindTest extends ApplicationTest {

    private static final String SAVE_REQUEST = loadResourceData("feature/finding/successful/save_request.json");
    private static final String FIND_REQUEST = loadResourceData("feature/finding/successful/find_request.json");
    private static final String EXPECTED_FIND_RESPONSE = loadResourceData("feature/finding/successful/find_response.json");
    private static final String NOT_FOUND_FIND_REQUEST = loadResourceData("feature/finding/not_found/find_request.json");

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
                .assertThat().body(Matchers.isEmptyOrNullString());
    }

}
