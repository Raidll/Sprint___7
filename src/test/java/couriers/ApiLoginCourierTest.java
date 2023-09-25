package couriers;

import baseURL.BaseURL;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import random.RandomString;

public class ApiLoginCourierTest {
    private CourierAllMethods courierAllMethods = new CourierAllMethods();
    private int id;

    @Before
    public  void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Test
    @DisplayName("Check response /api/v1/courier/login")
    public void loginCourier(){
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.create(courier);
        Response loginResponse = courierAllMethods.login(courier);
        loginResponse.then()
                .assertThat()
                .statusCode(200);
        id = loginResponse.path("id");

    }

    @Test
    @DisplayName("Check response with non-existent login and password /api/v1/courier/login")
    public void loginCourierWithNonExistentLoginAndPassword(){
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        Response loginResponse = courierAllMethods.login(courier);
        loginResponse.then()
                .assertThat()
                .statusCode(404);
    }

    @After
    public void deleteCourier() {
        courierAllMethods.delete(id);
    }
}
