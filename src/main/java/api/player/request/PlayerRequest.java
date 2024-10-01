package api.player.request;

import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.ResponseData;
import api.player.response.models.PlayerCreateResponseDTO;
import api._general.models.PlayerRole;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class PlayerRequest extends BaseRequest {
    public PlayerRequest() {
    }


    public ResponseData createPlayer(PlayerRole editor, PlayerCreateResponseDTO params, int statusCode) {
        addParams(super.specificationBuilder, params);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/create/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()))
                        .build();

        return this.get(specification, statusCode);
    }

    public ResponseData getPlayerByID(Integer playerID, int statusCode) {
        Map<String, Integer> body = new HashMap<>();
        body.put("playerId", playerID);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/get")
                        .build();

        return this.post(specification, new Gson().toJson(body), statusCode);
    }

    public ResponseData getAllPlayers(int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/get/all")
                        .build();

        return this.get(specification, statusCode);
    }

    public ResponseData updatePlayer(PlayerRole editor, Integer playerId, PlayerUpdateRequestDTO body, int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/update/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()) + "/" + playerId)
                        .build();

        return this.patch(specification, new Gson().toJson(body), statusCode);
    }

    public ResponseData deletePlayer(PlayerRole editor, Integer playerID, int statusCode) {
        Map<String, Integer> body = new HashMap<>();
        body.put("playerId", playerID);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/delete/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()))
                        .build();

        return this.delete(specification, new Gson().toJson(body), statusCode);
    }

    private ResponseData get(RequestSpecification specification, int statusCode) {
        return new ResponseData(RestAssured
                .given()
                    .spec(specification)
                    .log().all()
                .when()
                    .get()
                .then()
                    .log().body()
                    .statusCode(statusCode));
    }

    private ResponseData post(RequestSpecification specification, String body, int statusCode) {
        return new ResponseData(RestAssured
                .given()
                    .spec(specification)
                    .body(body)
                    .log().all()
                .when()
                    .post()
                .then()
                    .log().body()
                    .statusCode(statusCode));
    }

    private ResponseData patch(RequestSpecification specification, String body, int statusCode) {
        return new ResponseData(RestAssured
                .given()
                    .spec(specification)
                    .body(body)
                    .log().all()
                .when()
                    .patch()
                .then()
                    .log().body()
                    .statusCode(statusCode));
    }

    private ResponseData delete(RequestSpecification specification, String body, int statusCode) {
        return new ResponseData(RestAssured
                .given()
                    .spec(specification)
                    .body(body)
                    .log().all()
                .when()
                    .delete()
                .then()
                    .log().body()
                    .statusCode(statusCode));
    }
}
