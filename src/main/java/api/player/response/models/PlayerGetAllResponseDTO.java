package api.player.response.models;

import api._general.models.Gender;
import api._general.models.PlayerRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@EqualsAndHashCode
@AllArgsConstructor
public class PlayerGetAllResponseDTO {
    private Integer id;
    private String screenName;
    private PlayerRole role;
    private Gender gender;
    private Integer age;
}
