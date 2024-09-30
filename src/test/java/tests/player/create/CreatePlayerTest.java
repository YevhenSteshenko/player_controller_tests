package tests.player.create;

import api.requset.player.PlayerRequest;
import api.response.models.player.PlayerCreateResponseDTO;
import api.response.models.player.general.Gender;
import api.response.models.player.general.PlayerRole;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.ToNumberPolicy;
import com.google.gson.reflect.TypeToken;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreatePlayerTest {

    @Test(description = "Create player"
            , dataProvider = "validData")
    public void testCreatePlayer(PlayerRole editor, PlayerCreateResponseDTO player, int statusCode) {
        PlayerCreateResponseDTO actualResult = new PlayerRequest()
                .createPlayer(editor, player, statusCode)
                .as(PlayerCreateResponseDTO.class);


    }

    @Test(description = "Create player(negative scenarios)"
            , dataProvider = "invalidData")
    public void testCreatePlayerInvalidData(PlayerRole editor, PlayerCreateResponseDTO player, int statusCode, String errorMessage) {
        new PlayerRequest()
                .createPlayer(editor, player, statusCode)
                .as(PlayerCreateResponseDTO.class);
    }

    @DataProvider
    private Object[][] validData() {
        return new Object[][]{
                new Object[]
                        //POSITIVE
                        {PlayerRole.SUPERVISOR, PlayerCreateResponseDTO.generateData(Gender.MALE, PlayerRole.USER), 200},
                        {PlayerRole.SUPERVISOR, PlayerCreateResponseDTO.generateData(Gender.FEMALE, PlayerRole.ADMIN), 200},
        };
    }

    @DataProvider
    private Object[][] invalidData() {
        return new Object[][]{
                new Object[]
                        //NEGATIVE
                        {PlayerRole.USER, PlayerCreateResponseDTO.generateData(Gender.MALE, PlayerRole.USER), 403, ""},
                        {PlayerRole.USER, PlayerCreateResponseDTO.generateData(Gender.FEMALE, PlayerRole.ADMIN), 403, ""},
                        {PlayerRole.ADMIN, PlayerCreateResponseDTO.generateData(Gender.MALE, PlayerRole.USER), 403, ""},
                        {PlayerRole.ADMIN, PlayerCreateResponseDTO.generateData(Gender.FEMALE, PlayerRole.ADMIN), 403, ""},
                        {PlayerRole.SUPERVISOR, PlayerCreateResponseDTO.generateData(Gender.FEMALE, PlayerRole.NONEXISTENT), 403, ""},
                        {PlayerRole.SUPERVISOR, PlayerCreateResponseDTO.generateData(Gender.MALE, PlayerRole.SUPERVISOR), 403, ""},
                        {PlayerRole.NONEXISTENT, PlayerCreateResponseDTO.generateData(Gender.FEMALE, PlayerRole.USER), 403, ""},
                        {PlayerRole.NONEXISTENT, PlayerCreateResponseDTO.generateData(Gender.MALE, PlayerRole.ADMIN), 403, ""},
        };
    }
}
