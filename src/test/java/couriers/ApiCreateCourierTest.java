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
import static org.apache.http.HttpStatus.*;


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
        Response createResponse = courierAllMethods.createCourier(courier);
        createResponse.then().assertThat()
                .body(equalTo("{\"ok\":true}"))
                .and()
                .statusCode(SC_CREATED);
        Response loginResponse = courierAllMethods.loginCourier(courier);
        id = loginResponse.path("id");
    }

    @Test
    @DisplayName("Checking error when creating a duplicate courier")
    public void createDuplicateCourierTest(){
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.createCourier(courier);
        Response createDuplicateResponse = courierAllMethods.createCourier(courier);
        createDuplicateResponse.then().assertThat().statusCode(SC_CONFLICT);
        assertEquals("Этот логин уже используется", createDuplicateResponse.path("message"));

        Response loginResponse = courierAllMethods.loginCourier(courier);
        id = loginResponse.path("id");
    }

    @After
    public void deleteCourier() {
        courierAllMethods.deleteCourier(id);
    }
}
