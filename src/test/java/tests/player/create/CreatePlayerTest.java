package tests.player.create;

import _annotations_description.BackendEpic;
import _annotations_description.BackendFeature;
import _annotations_description.BackendStory;
import api.player.request.PlayerRequest;
import api.player.response.models.PlayerCreateResponseDTO;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.player.PlayerHelper;

@Epic(BackendEpic.API)
@Feature(BackendFeature.PLAYER)
@Stories(@Story(BackendStory.CREATE))
public class CreatePlayerTest {

    @Issue("2")
    @Test(description = "Create player"
            , dataProvider = "validData")
    public void testCreatePlayer(PlayerRole editor, PlayerCreateResponseDTO player) {
        PlayerCreateResponseDTO actualResult = new PlayerRequest()
                .createPlayer(editor, player, 200)
                .as(PlayerCreateResponseDTO.class);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertNotNull(actualResult.id(), "id: ");
        softAssert.assertEquals(actualResult.login(), player.login(), "login: ");
        softAssert.assertEquals(actualResult.password(), player.password(), "password: ");
        softAssert.assertEquals(actualResult.age(), player.age(), "age: ");
        softAssert.assertEquals(actualResult.gender(), player.gender(), "gender: ");
        softAssert.assertEquals(actualResult.screenName(), player.screenName(), "screenName: ");
        softAssert.assertEquals(actualResult.role(), player.role(), "role: ");
        softAssert.assertAll();
    }

    @Issue("4")
    @Test(description = "Try to create existed Player")
    public void testCreateExistPlayer() {
        PlayerCreateResponseDTO player = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, player, 200);
        PlayerCreateResponseDTO newPlayer = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, newPlayer.login(player.login()), 400);
    }

    @Issue("3")
    @Test(description = "Create player(negative scenarios)"
            , dataProvider = "invalidData")
    public void testCreatePlayerWithInvalidData(PlayerRole editor, PlayerCreateResponseDTO player, int statusCode) {
        new PlayerRequest()
                .createPlayer(editor, player, statusCode);
    }

    @DataProvider
    private Object[][] validData() {
        return new Object[][]{
                new Object[]
                        //POSITIVE
                        //Positive Scenarios
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(17)},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.ADMIN).age(60)},
        };
    }

    @DataProvider
    private Object[][] invalidData() {
        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        //Try to Create Player with incorrect options for different Editor type
                        {PlayerRole.USER, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER), 403},
                        {PlayerRole.USER, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.ADMIN), 403},
                        {PlayerRole.ADMIN, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER), 403},
                        {PlayerRole.ADMIN, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.ADMIN), 403},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.NONEXISTENT), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.SUPERVISOR), 400},
                        {PlayerRole.NONEXISTENT, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.USER), 403},
                        {PlayerRole.NONEXISTENT, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.ADMIN), 403},

                        //Try to Create Player with incorrect or empty mandatory fields
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).login(null), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).password(null), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(null), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(16), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(61), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).gender(null), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).gender(Gender.NONEXISTENT), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).screenName(null), 400},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).role(null), 400},

                        //Try to Create Player with none Editor
                        {PlayerRole.NONE, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.USER), 404},
        };
    }
}
