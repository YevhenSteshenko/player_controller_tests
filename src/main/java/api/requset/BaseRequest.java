package api.requset;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.ToNumberStrategy;
import com.google.gson.reflect.TypeToken;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class BaseRequest {
    protected RequestSpecBuilder specificationBuilder;

    public BaseRequest() {
        this.specificationBuilder = new RequestSpecBuilder()
                .setBaseUri("http://3.68.165.45") //todo: switch to get from property
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.ANY);
    }

    public RequestSpecBuilder addParams(RequestSpecBuilder specificationBuilder, Object object) {
        Gson gson = new GsonBuilder().setObjectToNumberStrategy(ToNumberPolicy.LONG_OR_DOUBLE).create();
        String json = gson.toJson(object);

        return specificationBuilder.addParams(gson.fromJson(json, new TypeToken<Map<String, Object>>() {}.getType()));
    }
}
