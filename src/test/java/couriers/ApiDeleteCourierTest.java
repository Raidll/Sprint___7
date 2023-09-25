package couriers;

import baseURL.BaseURL;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import random.RandomString;

import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

public class ApiDeleteCourierTest {
    private static Courier courier;
    private int id;
    CourierAllMethods courierAllMethods = new CourierAllMethods();

    @Before
    public  void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();

        courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.create(courier);
        Response responseLogin = courierAllMethods.login(courier);
        id = responseLogin.path("id");
    }

    @Test
    @DisplayName("Check response /api/v1/courier/:id")
    public void deleteCourierResponseTest(){
        Response responseDelete = courierAllMethods.delete(id);
        assertEquals(200, responseDelete.statusCode());
        assertEquals(true, responseDelete.path("ok"));
    }

    @Test
    @DisplayName("Check response bad data /api/v1/courier/:id")
    public void deleteCourierWithEmptyId(){
        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .delete("/api/v1/courier/");
        assertEquals(400, response.statusCode());
        assertEquals("Недостаточно данных для удаления курьера", response.path("message"));
    }

    @Test
    @DisplayName("Check delete courier with invalid Id")
    public void deleteCourierWithInvalidId(){
        Random random = new Random();
        Response responseDelete = courierAllMethods.delete(random.nextInt());
        assertEquals(404, responseDelete.statusCode());
        assertEquals("Курьера с таким id нет", responseDelete.path("message"));
    }

    @After
    public void deleteCourier() {
        courierAllMethods.delete(id);
    }


}
