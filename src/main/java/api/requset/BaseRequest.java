package api.requset;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

public class BaseRequest {
    protected RequestSpecBuilder specificationBuilder;

    public BaseRequest() {
        this.specificationBuilder = new RequestSpecBuilder()
                .setBaseUri("https://jsonplaceholder.typicode.com/")
                .setContentType(ContentType.JSON);
    }
}
