package api.player.request;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.Map;

public abstract class BaseRequest {
    @Getter
    @Accessors(fluent = true)
    protected RequestSpecBuilder specificationBuilder;

    public BaseRequest() {
        this.specificationBuilder = new RequestSpecBuilder()
                .setBaseUri("http://3.68.165.45") //todo: switch to get from property
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.ANY)
                .addFilter(new AllureRestAssured())
        ;
    }

    public RequestSpecBuilder addParams(RequestSpecBuilder specificationBuilder, Object object) {
        Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
        String json = gson.toJson(object);

        return specificationBuilder.addParams(gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType()));
    }
}
