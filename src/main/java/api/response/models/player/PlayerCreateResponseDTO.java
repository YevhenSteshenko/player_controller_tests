package api.response.models.player;


import _helpers.Generator;
import api.response.models.player.general.Gender;
import api.response.models.player.general.PlayerRole;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter(onMethod = @__(@JsonProperty))
@Setter
@EqualsAndHashCode(callSuper = true)
public class PlayerCreateResponseDTO extends PlayerItemDTO {
    private String login;
    private String password;

    public static PlayerCreateResponseDTO generateData(Gender gender, PlayerRole playerRole) {
        return (PlayerCreateResponseDTO) new PlayerCreateResponseDTO()
                .login(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .password(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .age(Generator.randomInt(5, 40))
                .gender(gender)
                .screenName(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .role(playerRole)
                ;
    }
}
