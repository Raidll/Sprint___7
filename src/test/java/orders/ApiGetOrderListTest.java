package orders;

import baseURL.BaseURL;
import couriers.Courier;
import couriers.CourierAllMethods;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import random.RandomString;
import static org.apache.http.HttpStatus.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ApiGetOrderListTest {
    OrdersAllMethods ordersAllMethods = new OrdersAllMethods();

    @Before
    public  void setUp() {
        RestAssured.baseURI = BaseURL.getBaseURL();
    }

    @Test
    @DisplayName("Check get order list /api/v1/orders")
    public void getOrderListTest(){
        Response getOrderListResponse = ordersAllMethods.getOrders("");
        assertEquals(SC_OK, getOrderListResponse.statusCode());
    }

    @Test
    @DisplayName("Check get order with courier ID /api/v1/orders?courierId=")
    public void getOrderListWithCourierIdTest(){
        int courierId = 0;
        CourierAllMethods courierAllMethods = new CourierAllMethods();
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.createCourier(courier);
        courierId = courierAllMethods.loginCourier(courier).path("id");

        Response getOrderListWithCourierIdResponse = ordersAllMethods.getOrderListWithCourierId(String.valueOf(courierId));
        assertEquals(SC_OK, getOrderListWithCourierIdResponse.statusCode());
        assertNotNull(getOrderListWithCourierIdResponse.path("orders"));

        courierAllMethods.deleteCourier(courierId);
    }

    @Test
    @DisplayName("Check get order with empty courier ID /api/v1/orders?courierId=")
    public void getOrderListWithInvalidCourierIdTest(){
        Response getOrderListWithCourierIdResponse = ordersAllMethods.getOrderListWithCourierId("999999");
        assertEquals(SC_NOT_FOUND, getOrderListWithCourierIdResponse.statusCode());
    }

}
