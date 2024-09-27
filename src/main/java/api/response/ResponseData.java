package api.response;

import api.response.models.posts.BaseResponse;
import io.restassured.response.Response;

import java.util.List;

public class ResponseData {
    private Response response;

    public ResponseData(Response response) {
        this.response = response;
    }

    public <T extends BaseResponse> T as(Class<T> clazz) {
        return response.as(clazz);
    }

    public <T extends BaseResponse> List<T> asList(String jsonPath, Class<T> clazz) {
        return response.jsonPath().getList(jsonPath, clazz);
    }
}
