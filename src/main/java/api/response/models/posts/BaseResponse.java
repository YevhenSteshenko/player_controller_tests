package api.response.models.posts;

import com.google.gson.Gson;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public abstract class BaseResponse {
    @Override
    public String toString() {
        return new Gson().newBuilder().setPrettyPrinting().create().toJson(this);
    }
}
