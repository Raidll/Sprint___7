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
        assertEquals(200, getOrderListResponse.statusCode());
    }

    @Test
    @DisplayName("Check get order with courier ID /api/v1/orders?courierId=")
    public void getOrderListWithCourierIdTest(){
        int courierId = 0;
        CourierAllMethods courierAllMethods = new CourierAllMethods();
        Courier courier = new Courier(RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5), RandomString.generateRandomHexString(5));
        courierAllMethods.create(courier);
        courierId = courierAllMethods.login(courier).path("id");

        Response getOrderListWithCourierIdResponse = ordersAllMethods.getOrderListWithCourierId(String.valueOf(courierId));
        assertEquals(200, getOrderListWithCourierIdResponse.statusCode());
        assertNotNull(getOrderListWithCourierIdResponse.path("orders"));

        courierAllMethods.delete(courierId);
    }

    @Test
    @DisplayName("Check get order with empty courier ID /api/v1/orders?courierId=")
    public void getOrderListWithInvalidCourierIdTest(){
        Response getOrderListWithCourierIdResponse = ordersAllMethods.getOrderListWithCourierId("999999");
        assertEquals(404, getOrderListWithCourierIdResponse.statusCode());
    }

}
