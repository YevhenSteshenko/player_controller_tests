package api.player.request.models;


import api._general.models.Gender;
import api._general.models.PlayerRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@EqualsAndHashCode
public class PlayerCreateRequestDTO {
    private String login;
    private String password;
    private String screenName;
    private Gender gender;
    private PlayerRole role;
    private Integer age;            //17-60

    @Override
    public String toString() {
        return new Gson().newBuilder().setPrettyPrinting().create().toJson(this);
    }
}
