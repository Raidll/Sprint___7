package couriers;

import baseURL.BaseURL;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import random.RandomString;
import java.util.Arrays;
import java.util.Collection;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.apache.http.HttpStatus.*;

@RunWith(Parameterized.class)
public class ApiCreateCourierParametrizeTest {
    private String requestBody;
    private static String randomValue = RandomString.generateRandomHexString(5);

    public ApiCreateCourierParametrizeTest(String requestBody) {
        this.requestBody = requestBody;
    }

    @Before
    public  void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testBadData() {
        return Arrays.asList(new Object[][]{
                {"{\"login\": \"" + randomValue + "\", \"firstName\": \"" + randomValue + "\"}"},
                {"{\"password\": \"" + randomValue + "\", \"firstName\": \"" + randomValue + "\"}"},
                {"{\"login\": \"" + randomValue + "\"}"},
                {"{\"password\": \"" + randomValue + "\"}"},
                {"{\"firstName\": \"" + randomValue + "\"}"}
        });
    }

    @Test
    @DisplayName("Check status code wen request with bad parameters")
    public void createCourierWithBadParameters(){
        Response response = given()
                .header("Content-type", "application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/courier");
        assertEquals(SC_BAD_REQUEST, response.statusCode());
        assertEquals("Недостаточно данных для создания учетной записи", response.path("message"));


    }
}
