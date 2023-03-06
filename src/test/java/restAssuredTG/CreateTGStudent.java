package restAssuredTG;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class CreateTGStudent {
    Faker faker = new Faker();
    Response response;
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://tech-global-training.com");
        requestSpecBuilder.setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }
    @Test(priority = 1, description = "Validate POST,GET,PUT and DELETE TG_Students")
    public void validateCreateDeleteTGStudents() {
        System.out.println("=============POST TG Student============");
        PojoTG createUser = PojoTG.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .dob("2002-01-24")
                .build();
        response = RestAssured
                .given()
                .body(createUser)
                .when().post("/students")
                .then().statusCode(200)
                .extract().response();

        int studentId = response.jsonPath().getInt("id");

        System.out.println("=============GET ALL TG Student============");
        response = RestAssured
                .given()
                .body(createUser)
                .when().get("/students")
                .then().statusCode(200)
                .extract().response();

        System.out.println("=============PUT TG Student============");
        response = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(PojoTG.builder()
                        .firstName(faker.name().firstName())
                        .lastName(faker.name().lastName())
                        .email(faker.internet().emailAddress())
                        .dob("2002-01-24")
                        .build())
                .when().put("/students/" + studentId)
                .then().statusCode(200)
                .extract().response();

        System.out.println("=============GET Specific TG Student============");
        response = RestAssured
                .given()
                .body(createUser)
                .when().get("/students/" + studentId)
                .then().statusCode(200)
                .extract().response();

        System.out.println("=============DELETE TG Student============");
        response = RestAssured
                .given()
                .body(createUser)
                .when().delete("/students/" + studentId)
                .then().statusCode(200)
                .extract().response();

        System.out.println("=============Make sure Student doesn't exists ============");
        response = RestAssured
                .given()
                .body(createUser)
                .when().get("/students/" + studentId)
                .then()
                .assertThat().statusCode(404)
                .extract().response();

    }
}