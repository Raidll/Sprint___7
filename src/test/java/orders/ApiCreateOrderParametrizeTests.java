package orders;

import baseURL.BaseURL;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;
import java.util.Arrays;
import java.util.Collection;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class ApiCreateOrderParametrizeTests {
    private OrdersAllMethods ordersAllMethods = new OrdersAllMethods();
    private JSONObject requestBody;
    private int track;

    public ApiCreateOrderParametrizeTests(JSONObject requestBody) {
        this.requestBody = requestBody;
    }


    @Before
    public void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        JSONObject attributes = new JSONObject()
                .put("firstName", "FirstName")
                .put("lastName", "LastName")
                .put("address", "Address")
                .put("metroStation", "Station")
                .put("phone", "1111111111")
                .put("rentTime", 1)
                .put("deliveryDate", "2023-09-28")
                .put("comment", "comment");
        return Arrays.asList(new Object[][]{
                {attributes.put("color", new JSONArray().put("BLACK").put("GRAY"))},
                {attributes.put("color", new JSONArray().put("BLACK"))},
                {attributes.put("color", new JSONArray().put("GRAY"))},
                {attributes.put("color", new JSONArray())}
        });
    }

    @Test
    @DisplayName("Check status code and response body /api/v1/orders")
    public void testCreateOrder() {
        Response responseCreateOrder = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/api/v1/orders");
        assertEquals(201, responseCreateOrder.statusCode());
        assertNotNull(responseCreateOrder.path("track"));
        track = responseCreateOrder.jsonPath().getInt("track");
    }

    @After
    public void clearCourier(){
        ordersAllMethods.delete(track);
    }
}
