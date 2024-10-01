package tests.player.get_player_id;

import _helpers.Generator;
import api.player.request.PlayerRequest;
import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.response.models.PlayerUpdateResponseDTO;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.player.PlayerHelper;

public class GetPlayerByIdTest {

    @Test(description = "POST /player/get returns correct data for present user and deleted")
    public void testGetPlayerById() {
        PlayerCreateResponseDTO newPlayerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        PlayerCreateResponseDTO createPlayerData = new PlayerRequest()
                .createPlayer(PlayerRole.SUPERVISOR, newPlayerData, 200)
                .as(PlayerCreateResponseDTO.class);
        PlayerCreateResponseDTO fetchPlayerData = new PlayerRequest()
                .getPlayerByID(createPlayerData.id(), 200).as(PlayerCreateResponseDTO.class);

        Assert.assertEquals(fetchPlayerData, newPlayerData.id(createPlayerData.id())
                , "There is a difference between Created Player Data and Fetch data");

        new PlayerRequest().deletePlayer(PlayerRole.SUPERVISOR, createPlayerData.id(), 204);
        new PlayerRequest()
                .getPlayerByID(createPlayerData.id(), 200)
                .asResponse().assertThat().body(Matchers.emptyOrNullString());
    }

    @Test(description = "")
    public void test() {
        PlayerCreateResponseDTO newPlayerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        PlayerCreateResponseDTO createdPlayerData = new PlayerRequest()
                .createPlayer(PlayerRole.SUPERVISOR, newPlayerData, 200)
                .as(PlayerCreateResponseDTO.class);

        PlayerUpdateRequestDTO playerUpdate = new PlayerUpdateRequestDTO()
                .login(Generator.randomString(20, "", Generator.CharType.LATIN))
                .password(newPlayerData.password())
                .screenName(newPlayerData.screenName())
                .gender(newPlayerData.gender())
                .role(newPlayerData.role())
                .age(newPlayerData.age());
        PlayerUpdateResponseDTO updatedPlayerData = new PlayerRequest()
                .updatePlayer(PlayerRole.SUPERVISOR, createdPlayerData.id(), playerUpdate, 200)
                .as(PlayerUpdateResponseDTO.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(updatedPlayerData.id(), createdPlayerData.id(), "id: ");
        softAssert.assertEquals(updatedPlayerData.login(), playerUpdate.login(), "login: ");
        softAssert.assertEquals(updatedPlayerData.screenName(), playerUpdate.screenName(), "screenName: ");
        softAssert.assertEquals(updatedPlayerData.gender(), playerUpdate.gender(), "gender: ");
        softAssert.assertEquals(updatedPlayerData.role(), playerUpdate.role(), "role: ");
        softAssert.assertEquals(updatedPlayerData.age(), playerUpdate.age(), "age: ");
        softAssert.assertAll();

        new PlayerRequest()
                .getPlayerByID(createdPlayerData.id(), 200)
                .asResponse().assertThat().body("password", Matchers.comparesEqualTo(playerUpdate.password()));
    }
}
