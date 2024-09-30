package api.response.models.player;


import api.response.models.BaseResponseDTO;
import api.response.models.player.general.Gender;
import api.response.models.player.general.PlayerRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlayerItemDTO extends BaseResponseDTO {
    private Integer id;
    private String screenName;
    private Gender gender;
    private PlayerRole role;
    private Integer age; //17-60
}
