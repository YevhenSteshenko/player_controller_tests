package tests.player.delete;

import _annotations_description.BackendEpic;
import _annotations_description.BackendFeature;
import _annotations_description.BackendStory;
import _common.utils.core.AwaitilityHelper;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.response.models.PlayerCreateResponseDTO;
import api.player.response.models.PlayerGetAllResponseDTO;
import com.google.gson.Gson;
import io.qameta.allure.*;
import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import tests.player.PlayerHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Epic(BackendEpic.API)
@Feature(BackendFeature.PLAYER)
@Stories(@Story(BackendStory.DELETE))
public class DeletePlayerTest {
    private PlayerCreateResponseDTO createdPlayerData;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {
        PlayerCreateResponseDTO newPlayerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        this.createdPlayerData = new PlayerRequest()
                .createPlayer(PlayerRole.SUPERVISOR, newPlayerData, 200)
                .as(PlayerCreateResponseDTO.class);
    }

    @Test(groups = {"all", "parallel"}
            , description = "Delete Player"
            )
    public void testDeletePlayer() {
        new PlayerRequest().deletePlayer(PlayerRole.SUPERVISOR, this.createdPlayerData.id(), 204);
        AwaitilityHelper.await()
                .alias("Wait when Player will be deleted")
                .ignoreExceptions()
                .pollInterval(1, TimeUnit.SECONDS)
                .atMost(10, TimeUnit.SECONDS)
                .until(() -> {
                    List<PlayerGetAllResponseDTO> allPlayers =
                            new PlayerRequest()
                                    .getAllPlayers(200)
                                    .asList("players", PlayerGetAllResponseDTO.class);

                    this.id(this.createdPlayerData.id().toString());

                    return allPlayers.stream().noneMatch(p -> p.id().equals(this.createdPlayerData.id()));
                });

        new PlayerRequest()
                .getPlayerByID(this.createdPlayerData.id(), 200)
                .asResponse().assertThat().body(Matchers.emptyOrNullString());
    }

    @Step("id: {id}")
    public void id(String id) {

    }

    @Issue(value = "5")
    @Test(groups = {"all", "parallel"}
            , description = "Try to Delete Player with wrong Editor"
            , dataProvider = "wrongEditorsForDeleteUserOperation"
    )
    public void testDeletePlayerWithWrongEditor(PlayerRole role, int statusCode) {
        new PlayerRequest().deletePlayer(role, this.createdPlayerData.id(), statusCode);
    }

    @Test(groups = {"all", "parallel"}
            , description = "Try to Delete Player with wrong body params"
            , dataProvider = "wrongBodyForDeleteUserOperation"
    )
    public void testDeletePlayerWithWrongBodyParams(PlayerRole role, String body, int statusCode, String message) {
        new PlayerRequest().deletePlayer(role, body, statusCode);
    }

    @DataProvider
    private Object[][] wrongEditorsForDeleteUserOperation() {
        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        //Try delete player with wrong Editor
                        {PlayerRole.ADMIN, 403},
                        {PlayerRole.USER, 403},
                        {PlayerRole.NONEXISTENT, 403},
                        {PlayerRole.NONE, 404},
        };
    }

    @DataProvider
    private Object[][] wrongBodyForDeleteUserOperation() {
        Map<String, Object> invalidParameter = new HashMap<>();
        invalidParameter.put("1", "d");

        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        //Try to delete Player with wrong body params
                        {PlayerRole.SUPERVISOR, "", 400, "Empty body"},
                        {PlayerRole.SUPERVISOR, "{}", 403, "Empty JSON"},
                        {PlayerRole.SUPERVISOR, new Gson().toJson(invalidParameter), 403, "Non-existent params in body"},
        };
    }
}
