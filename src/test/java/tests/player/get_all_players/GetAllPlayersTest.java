package tests.player.get_all_players;

import _annotations_description.BackendEpic;
import _annotations_description.BackendFeature;
import _annotations_description.BackendStory;
import api._general.models.Gender;
import api._general.models.PlayerRole;
import api.player.request.PlayerRequest;
import api.player.request.models.PlayerCreateRequestDTO;
import api.player.response.models.PlayerCreateResponseDTO;
import api.player.response.models.PlayerGetAllResponseDTO;
import io.qameta.allure.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import tests.BaseTest;
import tests.player.PlayerHelper;

import java.util.ArrayList;
import java.util.List;

@Epic(BackendEpic.API)
@Feature(BackendFeature.PLAYER)
@Stories(@Story(BackendStory.GET_ALL_PLAYERS))
public class GetAllPlayersTest extends BaseTest {
    private final List<PlayerCreateResponseDTO> players = new ArrayList<>();
    @BeforeClass(alwaysRun = true)
    public void beforeClass() {
        while (this.players.size() < 3) {
            PlayerCreateRequestDTO playerData = PlayerHelper.generatePlayerCreateData(Gender.MALE, PlayerRole.USER);
            PlayerCreateResponseDTO createdPlayer = new PlayerRequest()
                    .createPlayer(PlayerRole.SUPERVISOR, playerData, 200)
                    .as(PlayerCreateResponseDTO.class);

            this.players.add(createdPlayer
                    .login(playerData.login())
                    .password(playerData.password())
                    .screenName(playerData.screenName())
                    .age(playerData.age())
                    .role(playerData.role())
                    .gender(playerData.gender()));
        }
    }

    @Issue(value = "8")
    @Test(description = "Validate Get All Players data"
            , groups = {"all", "get_all_players"}
    )
    public void testGetAllPlayers() {
        List<PlayerGetAllResponseDTO> allPlayers = new PlayerRequest().getAllPlayers(200)
                .asList("players", PlayerGetAllResponseDTO.class);

        SoftAssert softAssert = new SoftAssert();
        players.forEach(createdPlayer -> {
            PlayerGetAllResponseDTO playerFromCollection = allPlayers.stream()
                    .filter(p -> p.id().equals(createdPlayer.id()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Player with ID:  " + createdPlayer.id() + "  not found!"));
            softAssert.assertEquals(playerFromCollection.screenName(), createdPlayer.screenName(), "playerID: " + createdPlayer.id() + " screenName: ");
            softAssert.assertEquals(playerFromCollection.role(), createdPlayer.role(), "playerID: " + createdPlayer.id() + " role: ");
            softAssert.assertEquals(playerFromCollection.gender(), createdPlayer.gender(), "playerID: " + createdPlayer.id() + " gender: ");
            softAssert.assertEquals(playerFromCollection.age(), createdPlayer.age(), "playerID: " + createdPlayer.id() + " age: ");
        });
        softAssert.assertAll();
    }
}
