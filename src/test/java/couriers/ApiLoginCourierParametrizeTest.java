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

@RunWith(Parameterized.class)
public class ApiLoginCourierParametrizeTest {
    private  final String login;
    private final String password;
    private final int expectedErrorCode;
    private final String expectedErrorMessage;

    public ApiLoginCourierParametrizeTest(String login, String password, int expectedErrorCode, String expectedErrorMessage) {
        this.login = login;
        this.password = password;
        this.expectedErrorCode = expectedErrorCode;
        this.expectedErrorMessage = expectedErrorMessage;
    }


    @Before
    public void setUp(){
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> testBadData() {
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));

        return Arrays.asList(new Object[][]{
                {courier.getLogin(), "", 400, "Недостаточно данных для входа"},
                {"", courier.getPassword(), 400, "Недостаточно данных для входа"},
                {"", "", 400, "Недостаточно данных для входа"}
        });
    }

    @Test
    @DisplayName("Check login courier with invalid parameters /api/v1/courier/login")
    public void loginCourierWithBadParameters(){
       Response response = given()
                .header("Content-type", "application/json")
                .body("\"login\": \"" + login + "\",\"" + password + "\": \"1234\"")
                .when()
                .post("/api/v1/courier/login");
       assertEquals(expectedErrorCode, response.statusCode());
       assertEquals(expectedErrorMessage, response.path("message"));

    }
}
