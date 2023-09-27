package orders;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrdersAllMethods {
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String GET_ORDER_LIST = "/api/v1/orders";
    private  static final String GET_ORDER_LIST_WITH_COURIER_ID = "/api/v1/orders?courierId=";
    private static final String DELETE = "/api/v1/orders/cancel";

    public Response create(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER);
    }

    public Response getOrders(String order){
        return given()
                .contentType("application/json")
                .when()
                .get(GET_ORDER_LIST + order);
    }

    public Response getOrderListWithCourierId(String courierId){
        return given()
                .contentType("application/json")
                .when()
                .get(GET_ORDER_LIST_WITH_COURIER_ID + courierId);
    }

    public Response delete(int track){
        return given()
                .contentType("application/json")
                .queryParam("track", track)
                .when()
                .put(DELETE);
    }
}
