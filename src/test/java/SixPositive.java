import org.testng.annotations.Test;
import io.restassured.RestAssured;
import static org.hamcrest.Matchers.*;

public class SixPositive {
    public SixPositive() {
        RestAssured.baseURI = "https://gorest.co.in";
    }

    // GET /public-api/users
    @Test
    public void GetUsersTest(){
        // Проверяем статус
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().get("/public-api/users").then().statusCode(200);
    }

    // GET /public-api/users?first_name=<user_name>
    @Test
    public void GetUserByNameTest(){
        // Проверяем статус и правильность выборки
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().get("/public-api/users?first_name=Alex").then().statusCode(200)
                .assertThat().body("result", notNullValue())
                .body("result[0].first_name", containsString("Alex"));
    }

    // POST /public-api/users
    @Test
    public void CreateUserTest(){
        // Проверяем статус и правильность создания
        RestAssured.given().formParams("email", "12312dsasf3@gmail.com", "first_name",
                "Lexik", "last_name", "Bit", "gender", "female")
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().post("/public-api/users").then().statusCode(302).assertThat()
                .body("result.first_name", equalTo("Lexik"));
    }

    // GET /public-api/users/<user_id>
    @Test
    public void GetUserByIdTest(){
        // Проверяем статус и правильность выборки
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().get("/public-api/users/31470").then().statusCode(200)
                .assertThat().body("result", notNullValue())
                .body("result.id", equalTo("31470"));
    }

    // PUT /public-api/users/<user_id>
    @Test
    public void UpdateUserTest(){
        // Проверяем статус и правильность обновления поля
        RestAssured.given()
                .formParams("first_name", "Ivan")
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().put("/public-api/users/31470").then().statusCode(200)
                .assertThat().body("result", notNullValue())
                .body("result.first_name", equalTo("Ivan"));
    }

    // DELETE /public-api/users/<user_id>
    @Test
    public void DeleteUserTest(){
        // Проверяем статус и правильность выборки
        RestAssured.given()
                .header("Authorization", "Bearer dfTu_0FgAlBd8jTaITSgL2f1GRMQkm0JUhN4")
                .when().delete("/public-api/users/1").then().statusCode(200);
    }
}