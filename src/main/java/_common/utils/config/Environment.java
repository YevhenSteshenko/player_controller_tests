package _common.utils.config;

import lombok.NonNull;

public enum Environment {
    URI("base.uri"),

    //Endpoints
    PLAYER_GET("player.get"),
    PLAYER_GET_ALL("player.get.all"),
    PLAYER_CREATE("player.create"),
    PLAYER_UPDATE("player.update"),
    PLAYER_DELETE("player.delete");

    private final String key;

    Environment(@NonNull final String key) {
        this.key = key;
    }

    public String getString() {
        return ConfigLoader.getValue(this.key);
    }

    public Integer getInt() {
        return Integer.parseInt(ConfigLoader.getValue(this.key));
    }
}
