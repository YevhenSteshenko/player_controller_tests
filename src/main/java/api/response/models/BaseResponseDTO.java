package api.response.models;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BaseResponseDTO {
    @Override
    public String toString() {
        return new Gson().newBuilder().setPrettyPrinting().create().toJson(this);
    }
}
