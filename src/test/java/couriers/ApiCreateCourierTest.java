package couriers;


import baseURL.BaseURL;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import random.RandomString;


public class ApiCreateCourierTest {

    private CourierAllMethods courierAllMethods = new CourierAllMethods();
    private int id;

    @Before
    public  void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Test
    @DisplayName("Check response /api/v1/courier/")
    public void createCourierTest(){
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        Response createResponse = courierAllMethods.create(courier);
        createResponse.then().assertThat()
                .body(equalTo("{\"ok\":true}"))
                .and()
                .statusCode(201);
        Response loginResponse = courierAllMethods.login(courier);
        id = loginResponse.path("id");
    }

    @Test
    @DisplayName("Checking error when creating a duplicate courier")
    public void createDuplicateCourierTest(){
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.create(courier);
        Response createDuplicateResponse = courierAllMethods.create(courier);
        createDuplicateResponse.then().assertThat().statusCode(409);
        assertEquals("Этот логин уже используется", createDuplicateResponse.path("message"));

        Response loginResponse = courierAllMethods.login(courier);
        id = loginResponse.path("id");
    }

    @After
    public void deleteCourier() {
        courierAllMethods.delete(id);
    }
}
