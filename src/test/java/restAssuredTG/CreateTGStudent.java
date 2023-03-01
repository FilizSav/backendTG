package restAssuredTG;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateTGStudent {
    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://tech-global-training.com");
        requestSpecBuilder.setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void validate_get_request_bdd() {
        given().
        when().
                get("/students").
        then().
                assertThat().statusCode(200);
    }
    @Test
    public void createStudent(){
        PojoTG student = new PojoTG("john","doe","johndoe@gmail.com","1992-12-15");
                given().
                body(student).
                when().post("/students").
                then().
                assertThat().statusCode(200);
    }
}