package api.requset;

import api.response.ResponseData;
import api.response.models.posts.UserResponse;
import io.restassured.RestAssured;

public class UsersRequest extends BaseRequest {
    private final String path;

    public UsersRequest() {
        this.path = "/users";

        super.specificationBuilder
                .setBasePath(this.path);
    }

    public ResponseData get(Integer statusCode) {

        return new ResponseData(RestAssured
                .given()
                .spec(this.specificationBuilder.build())
                .log().all()
                .when()
                .log().all()
                .get()
                .then()
                .log().all()
                .statusCode(statusCode).extract().response());
    }

    public ResponseData post() {
        //todo: implement

        return null;
    }

    public ResponseData put() {
        //todo: implement

        return null;
    }
}
