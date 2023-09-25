package couriers;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierAllMethods {
    private static final String LOGIN_COURIER = "/api/v1/courier/login";
    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String DELETE_COURIER = "/api/v1/courier/:id";



    public Response login(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(LOGIN_COURIER);
    }

    public Response create(Courier courier){
        return given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post(CREATE_COURIER);
    }

    public Response delete(int id) {
        return given()
                .header("Content-type", "application/json")
                .when()
                .delete(DELETE_COURIER.replace(":id",String.valueOf(id)));
    }
}
