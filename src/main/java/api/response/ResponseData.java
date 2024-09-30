package api.response;

import api.response.models.BaseResponseDTO;
import io.restassured.response.Response;

import java.util.List;

public class ResponseData {
    private Response response;

    public ResponseData(Response response) {
        this.response = response;
    }

    public <T extends BaseResponseDTO> T as(Class<T> clazz) {
        return response.as(clazz);
    }

    public <T extends BaseResponseDTO> List<T> asList(String jsonPath, Class<T> clazz) {
        return response.jsonPath().getList(jsonPath, clazz);
    }
}
