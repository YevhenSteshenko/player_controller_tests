package tests.player.get_player_id;

import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.response.models.PlayerCreateResponseDTO;
import com.google.gson.Gson;
import io.qameta.allure.Issue;
import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.player.PlayerHelper;

import java.util.HashMap;
import java.util.Map;

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

    @Issue("7")
    @Test(description = "Try to get Player Data via invalid requests", dataProvider = "invalidData")
    public void testGetPlayerByIdNegative(String body, int statusCode) {
        new PlayerRequest().getPlayerByID(body, statusCode);
    }

    @DataProvider
    private Object[][] invalidData() {
        Map<String, Object> invalidParameter = new HashMap<>();
        invalidParameter.put("1", "d");

        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        //Check Body validation schema
                        {"", 400},
                        {"{}", 400},
                        {new Gson().toJson(invalidParameter), 400},
        };
    }
}
