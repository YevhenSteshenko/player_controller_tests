package tests.player.create;

import _annotations_description.BackendEpic;
import _annotations_description.BackendFeature;
import _annotations_description.BackendStory;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.request.models.PlayerCreateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import io.qameta.allure.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import tests.player.PlayerHelper;

@Epic(BackendEpic.API)
@Feature(BackendFeature.PLAYER)
@Stories(@Story(BackendStory.CREATE))
public class CreatePlayerTest extends BaseTest {

    @Issue("2")
    @Test(description = "Create player"
            , groups = {"all", "create_player"}
            , dataProvider = "validData")
    public void testCreatePlayer(PlayerRole editor, PlayerCreateRequestDTO player) {
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
    @Test(description = "Try to create existed Player"
            , groups = {"all", "create_player"}
    )
    public void testCreateExistPlayer() {
        PlayerCreateRequestDTO player = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, player, 200);
        PlayerCreateRequestDTO newPlayer = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, newPlayer.login(player.login()), 400);
    }

    @Issue("3")
    @Test(description = "Create player(negative scenarios)"
            , groups = {"all", "create_player"}
            , dataProvider = "invalidData")
    public void testCreatePlayerWithInvalidData(PlayerRole editor, PlayerCreateRequestDTO player, int statusCode, String description) {
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
                        {PlayerRole.USER, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER), 403, "Incorrect Editor USER"},
                        {PlayerRole.USER, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.ADMIN), 403, "Incorrect Editor USER"},
                        {PlayerRole.ADMIN, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER), 403, "Incorrect Editor ADMIN"},
                        {PlayerRole.ADMIN, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.ADMIN), 403, "Incorrect Editor ADMIN"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.NONEXISTENT), 400, "Incorrect Role NONEXISTENT"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.SUPERVISOR), 400, "Incorrect Role SUPERVISOR"},
                        {PlayerRole.NONEXISTENT, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.USER), 403, "Incorrect Editor NONEXISTENT"},
                        {PlayerRole.NONEXISTENT, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.ADMIN), 403, "Incorrect Editor NONEXISTENT"},

                        //Try to Create Player with incorrect or empty mandatory fields
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).login(null), 400, "Empty mandatory field:login"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).password(null), 400, "Empty mandatory field:password"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(null), 400, "Empty mandatory field:age"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(16), 400, "Less then range of field:age=16"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(61), 400, "More then range of field:age=61"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).gender(null), 400, "Empty mandatory field:gender"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).gender(Gender.NONEXISTENT), 400, "Incorrect mandatory field:gender=NONEXISTENT"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).screenName(null), 400, "Empty mandatory field:screenName"},
                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).role(null), 400, "Empty mandatory field:role"},

                        //Try to Create Player with none Editor
                        {PlayerRole.NONE, PlayerHelper.generatePlayerCreateData(Gender.FEMALE, PlayerRole.USER), 404, "No Editor"},
        };
    }
}
