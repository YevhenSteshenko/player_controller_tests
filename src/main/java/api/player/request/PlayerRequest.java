package api.player.request;

import api._general.models.PlayerRole;
import api.player.request.models.PlayerUpdateRequestDTO;
import api.player.response.ResponseData;
import api.player.response.models.PlayerCreateResponseDTO;
import com.google.gson.Gson;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class PlayerRequest extends BaseRequest {
    @Step("Get Player by ID: {playerID}")
    public ResponseData getPlayerByID(Integer playerID, int statusCode) {
        Map<String, Integer> body = new HashMap<>();
        body.put("playerId", playerID);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/get")
                        .build();

        return this.post(specification, new Gson().toJson(body), statusCode);
    }

    @Step("Get Player by ID with body: {body}")
    public ResponseData getPlayerByID(String body, int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/get")
                        .build();

        return this.post(specification, body, statusCode);
    }

    @Step("Get All Players Data")
    public ResponseData getAllPlayers(int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/get/all")
                        .build();

        return this.get(specification, statusCode);
    }

    @Step("Create Player Request with editor: {editor} and query_params: {params}")
    public ResponseData createPlayer(PlayerRole editor, PlayerCreateResponseDTO params, int statusCode) {
        addParams(super.specificationBuilder, params);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/create/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()))
                        .build();

        return this.get(specification, statusCode);
    }

    @Step("Update Player Request with editor: {editor}, PlayerID: {playerID} and Body: {body}")
    public ResponseData updatePlayer(PlayerRole editor, Integer playerID, PlayerUpdateRequestDTO body, int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/update" + (editor.equals(PlayerRole.NONE) ? "" : ( "/" + editor.name().toLowerCase())) + "/" + playerID)
                        .build();

        return this.patch(specification, new Gson().toJson(body), statusCode);
    }

    @Step("Update Player Request with editor: {editor}, PlayerID: {playerID} and Body: {body}")
    public ResponseData updatePlayer(PlayerRole editor, Integer playerID, String body, int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/update" + (editor.equals(PlayerRole.NONE) ? "" : ( "/" + editor.name().toLowerCase())) + "/" + playerID)
                        .build();

        return this.patch(specification, body, statusCode);
    }

    @Step("Delete Player Request with editor: {editor} and PlayerID: {playerID}")
    public ResponseData deletePlayer(PlayerRole editor, Integer playerID, int statusCode) {
        Map<String, Integer> body = new HashMap<>();
        body.put("playerId", playerID);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/delete/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()))
                        .build();

        return this.delete(specification, new Gson().toJson(body), statusCode);
    }

    @Step("Delete Player Request with editor: {editor} and Body: {body}")
    public ResponseData deletePlayer(PlayerRole editor, String body, int statusCode) {
        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/delete/" + (editor.equals(PlayerRole.NONE) ? "" : editor.name().toLowerCase()))
                        .build();

        return this.delete(specification, body, statusCode);
    }

    @Step("Perform GET request with Spec: {specification}. Expected Response Code: {statusCode}")
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

    @Step("Perform POST request with Spec: {specification}. Body: {body} Expected Response Code: {statusCode}")
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

    @Step("Perform PATCH request with Spec: {specification}. Body: {body} Expected Response Code: {statusCode}")
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

    @Step("Perform DELETE request with Spec: {specification}. Body: {body} Expected Response Code: {statusCode}")
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
