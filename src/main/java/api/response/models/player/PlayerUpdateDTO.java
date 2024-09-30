package api.response.models.player;


import api.response.models.BaseResponseDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlayerUpdateDTO extends BaseResponseDTO {
    private String login;
}
