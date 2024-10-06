package tests.player.update;

import _annotations_description.BackendEpic;
import _annotations_description.BackendFeature;
import _annotations_description.BackendStory;
import _helpers.Generator;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.request.models.PlayerCreateRequestDTO;
import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import api.player.response.models.PlayerUpdateResponseDTO;
import com.google.gson.Gson;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.BaseTest;
import tests.player.PlayerHelper;

import java.util.HashMap;
import java.util.Map;

@Epic(BackendEpic.API)
@Feature(BackendFeature.PLAYER)
@Stories(@Story(BackendStory.UPDATE))
public class UpdatePlayerTest extends BaseTest {
    private PlayerCreateResponseDTO createdPlayerData;
    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        PlayerCreateRequestDTO newPlayerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        this.createdPlayerData = new PlayerRequest()
                .createPlayer(PlayerRole.SUPERVISOR, newPlayerData, 200)
                .as(PlayerCreateResponseDTO.class);

        //unnecessary action but now /player/create return in response not all data.
        //Could be remove after "@Issue 4" will be fixed
        createdPlayerData
                .login(newPlayerData.login())
                .password(newPlayerData.password())
                .screenName(newPlayerData.screenName())
                .age(newPlayerData.age())
                .role(newPlayerData.role())
                .gender(newPlayerData.gender());
    }

    @Issue("9")
    @Test(description = "Validate Update Player Data"
            , groups = {"all", "update_player"}
            , dataProvider = "validData")
    public void testUpdatePlayer(PlayerUpdateRequestDTO playerUpdateData) {
        PlayerUpdateResponseDTO updatedPlayerData = new PlayerRequest()
                .updatePlayer(PlayerRole.SUPERVISOR, this.createdPlayerData.id(), playerUpdateData, 200)
                .as(PlayerUpdateResponseDTO.class);

        PlayerUpdateResponseDTO expectedPlayerData = PlayerHelper.updateMapper(this.createdPlayerData, playerUpdateData);

        Assert.assertEquals(updatedPlayerData, expectedPlayerData, "Check if Player data has been updated: ");
        new PlayerRequest()
                .getPlayerByID(this.createdPlayerData.id(), 200)
                .asResponse().assertThat().body("password"
                        , Matchers.comparesEqualTo(playerUpdateData.password() != null
                                ? playerUpdateData.password()
                                : this.createdPlayerData.password()));
    }

    @Issue("10")
    @Test(description = "Try to updated Player with incorrect data"
            , groups = {"all", "update_player"}
            , dataProvider = "invalidData")
    public void testUpdatePlayerWithInvalidData(PlayerRole role, String playerUpdateData, int statusCode, String message) {
        new PlayerRequest()
                .updatePlayer(role, this.createdPlayerData.id(), playerUpdateData, statusCode);
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

    @DataProvider
    private Object[][] invalidData() {
        Map<String, Object> invalidParameter = new HashMap<>();
        invalidParameter.put("1", "d");

        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        //Try update with wrong Role
                        {PlayerRole.ADMIN, PlayerHelper.generatePlayerUpdateData(Gender.MALE, PlayerRole.USER).toString(), 403, "Update with incorrect Editor:ADMIN"},
                        {PlayerRole.USER, PlayerHelper.generatePlayerUpdateData(Gender.MALE, PlayerRole.USER).toString(), 403, "Update with incorrect Editor:USER"},
                        {PlayerRole.NONE, PlayerHelper.generatePlayerUpdateData(Gender.MALE, PlayerRole.USER).toString(), 404, "Update with incorrect Editor:NONE"},

                        //Try to update Player with wrong params
                        {PlayerRole.SUPERVISOR, "", 400, "Empty body"},
                        {PlayerRole.SUPERVISOR, "{}", 403, "Empty JSON"},
                        {PlayerRole.SUPERVISOR, new Gson().toJson(invalidParameter), 403, "Non-existent params in body"},


        };
    }
}
