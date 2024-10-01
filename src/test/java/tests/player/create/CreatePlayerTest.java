package tests.player.create;

import api.player.request.PlayerRequest;
import api.player.response.models.PlayerCreateResponseDTO;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import io.qameta.allure.Issue;
import io.qameta.allure.Issues;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.player.PlayerHelper;

public class CreatePlayerTest {

    @Issue("4")
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

    @Issue("8")
    @Test(description = "Create player(negative scenarios)")
    public void testCreateExistPlayer() {
        PlayerCreateResponseDTO player = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, player, 200);
        PlayerCreateResponseDTO newPlayer = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
        new PlayerRequest().createPlayer(PlayerRole.SUPERVISOR, newPlayer.login(player.login()), 400);
    }

    @Issues({@Issue("3"), @Issue("5"), @Issue("6")})
    @Test(description = "Create player(negative scenarios)"
            , dataProvider = "invalidData")
    public void testCreatePlayerInvalidData(PlayerRole editor, PlayerCreateResponseDTO player, int statusCode) {
        new PlayerRequest()
                .createPlayer(editor, player, statusCode);
    }

    @DataProvider
    private Object[][] validData() {
        return new Object[][]{
                new Object[]
                        //POSITIVE
                        //Positive Scenarios
//                        {PlayerRole.SUPERVISOR, PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER).age(17)},
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
