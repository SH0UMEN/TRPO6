import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class SixNegative {
    public SixNegative() {
        RestAssured.baseURI = "https://gorest.co.in";
    }

    // GET /public-api/users
    @Test
    public void NoTokenTest(){
        // Проверяем статус запроса без токена
        RestAssured.given().when().get("/public-api/users")
                .then().statusCode(200).assertThat()
                .body("result.status", equalTo(401))
                .body("result.message", equalTo("Your request was made with invalid credentials."));
    }

    // GET /public-api/users
    @Test
    public void IncorrectTokenTest(){
        // Проверяем статус запроса с невалидным токеном
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0Fsdaf231ddsdfdas3e1GRMQkm0JUhN4")
                .when().get("/public-api/users").then().statusCode(200).assertThat()
                .body("result.status", equalTo(401))
                .body("result.message", equalTo("Your request was made with invalid credentials."));
    }

    // POST /public-api/users
    @Test
    public void CreateUserTest(){
        // Проверяем статус и правильность ответа об отсутствии email
        RestAssured.given().formParams("first_name", "Lexik", "last_name", "Bit", "gender", "female")
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().post("/public-api/users").then().assertThat()
                .body("_meta.code", equalTo(422))
                .body("result[0].message", equalTo("Email cannot be blank."));
    }

    // DELETE /public-api/users/<user_id>
    @Test
    public void DeleteUserTest(){
        // Проверяем статус и правильность ответа об отсутствии пользователя с id=1
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().delete("/public-api/users/1").then().assertThat()
                .body("_meta.code", equalTo(404));
    }
}