package _common.utils.config;

import lombok.NonNull;

public enum Default {
    URI("base.uri");

    private final String key;

    Default(@NonNull final String key) {
        this.key = key;
    }

    public String getString() {
        return ConfigLoader.getValue(this.key);
    }
}
