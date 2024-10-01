package tests.player.update;

import _helpers.Generator;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import api.player.response.models.PlayerUpdateResponseDTO;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.player.PlayerHelper;

public class UpdatePlayerTest {
    @Test(description = "Validate Update Player Data"
            , dataProvider = "validData")
    public void testUpdatePlayer(PlayerUpdateRequestDTO playerUpdateData) {
        PlayerCreateResponseDTO newPlayerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        PlayerCreateResponseDTO createdPlayerData = new PlayerRequest()
                .createPlayer(PlayerRole.SUPERVISOR, newPlayerData, 200)
                .as(PlayerCreateResponseDTO.class);

        //unnecessary action but now /player/create return in response not all data.
        //Could be remove after "@Issue 4" will be fixed
        createdPlayerData = newPlayerData.id(createdPlayerData.id());

        PlayerUpdateResponseDTO updatedPlayerData = new PlayerRequest()
                .updatePlayer(PlayerRole.SUPERVISOR, createdPlayerData.id(), playerUpdateData, 200)
                .as(PlayerUpdateResponseDTO.class);

        PlayerUpdateResponseDTO expectedPlayerData = PlayerHelper.updateMapper(createdPlayerData, playerUpdateData);

        Assert.assertEquals(updatedPlayerData, expectedPlayerData, "Check if Player data has been updated: ");
        new PlayerRequest()
                .getPlayerByID(createdPlayerData.id(), 200)
                .asResponse().assertThat().body("password"
                        , Matchers.comparesEqualTo(playerUpdateData.password() != null
                                ? playerUpdateData.password()
                                : createdPlayerData.password()));
    }

    @DataProvider
    private Object[][] validData() {
        return new Object[][]{
                new Object[]
                        //POSITIVE
                        //Update Player fields separately
                        {new PlayerUpdateRequestDTO().login(Generator.randomString(10, "", Generator.CharType.LATIN))},
                        {new PlayerUpdateRequestDTO().password(Generator.randomString(15, "", Generator.CharType.LATIN))},
                        {new PlayerUpdateRequestDTO().screenName(Generator.randomString(20, "", Generator.CharType.LATIN))},
                        {new PlayerUpdateRequestDTO().age(Generator.randomInt(17, 60))},
                        {new PlayerUpdateRequestDTO().role(PlayerRole.ADMIN)},
                        {new PlayerUpdateRequestDTO().gender(Gender.FEMALE)},

                        //Update all Player fields
                        {PlayerHelper.generatePlayerUpdateData(Gender.MALE, PlayerRole.USER)},
        };
    }
}
