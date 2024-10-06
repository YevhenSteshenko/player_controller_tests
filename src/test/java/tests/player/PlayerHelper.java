package tests.player;

import _helpers.Generator;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.models.PlayerCreateRequestDTO;
import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import api.player.response.models.PlayerUpdateResponseDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PlayerHelper {
    public static PlayerCreateRequestDTO generatePlayerCreateData(Gender gender, PlayerRole playerRole) {
        return new PlayerCreateRequestDTO()
                .login(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .password(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .age(Generator.randomInt(17, 60))
                .gender(gender)
                .screenName(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .role(playerRole);
    }

    public static PlayerUpdateRequestDTO generatePlayerUpdateData(Gender gender, PlayerRole playerRole) {
        return new PlayerUpdateRequestDTO()
                .login(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .password(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .age(Generator.randomInt(17, 60))
                .gender(gender)
                .screenName(Generator.randomString(10, "", Generator.CharType.LATIN, Generator.CharType.DIGITS))
                .role(playerRole);
    }

    public static PlayerUpdateResponseDTO updateMapper(PlayerCreateResponseDTO playerData, PlayerUpdateRequestDTO updateData) {
        return new PlayerUpdateResponseDTO()
                .id(playerData.id())
                .login(updateData.login() != null ? updateData.login() : playerData.login())
                .screenName(updateData.screenName() != null ? updateData.screenName() : playerData.screenName())
                .age(updateData.age() != null ? updateData.age() : playerData.age())
                .role(updateData.role() != null ? updateData.role() : playerData.role())
                .gender(updateData.gender() != null ? updateData.gender() : playerData.gender());
    }
}
