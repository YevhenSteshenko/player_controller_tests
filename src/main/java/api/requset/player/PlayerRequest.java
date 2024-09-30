package api.requset.player;

import api.requset.BaseRequest;
import api.response.ResponseData;
import api.response.models.player.PlayerCreateResponseDTO;
import api.response.models.player.general.PlayerRole;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

//todo: add allure annotations
public class PlayerRequest extends BaseRequest {
    public PlayerRequest() {
    }


    public ResponseData createPlayer(PlayerRole editor, PlayerCreateResponseDTO body, Integer statusCode) {
        addParams(super.specificationBuilder, body);

        RequestSpecification specification =
                super.specificationBuilder
                        .setBasePath("/player/create/" + editor.name().toLowerCase())
                        .build();

        return new ResponseData(RestAssured
                .given()
                .spec(specification)
                .log().all()
                .when()
//                .log().all()
                .get()
                .then()
                .log().all()
                .statusCode(statusCode).extract().response());
    }

    public ResponseData post() {
        //todo: implement

        return null;
    }

    public ResponseData patch() {
        //todo: implement

        return null;
    }

    public ResponseData delete() {
        //todo: implement

        return null;
    }
}
