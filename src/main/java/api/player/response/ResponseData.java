package api.player.response;

import com.google.gson.Gson;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ValidatableResponse;

import java.util.List;

public class ResponseData {
    private final ValidatableResponse response;

    public ResponseData(ValidatableResponse response) {
        this.response = response;
    }

    public <T> T as(Class<T> clazz) {
        return new Gson().fromJson(response.extract().body().asString(), clazz);
    }

    public <T> List<T> asList(String jsonPath, Class<T> clazz) {
        return response.extract().jsonPath().getList(jsonPath, clazz);
    }

    public JsonPath asJsonPath() {
        return response.extract().jsonPath();
    }

    public ValidatableResponse asResponse() {
        return response;
    }
}
