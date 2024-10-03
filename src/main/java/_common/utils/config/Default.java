package _common.utils.config;

import lombok.NonNull;

public enum Default {
    THREAD_COUNT("thread.count");

    private final String key;

    Default(@NonNull final String key) {
        this.key = key;
    }

    public String getString() {
        return ConfigLoader.getValue(this.key);
    }

    public Integer getInt() {
        return Integer.parseInt(ConfigLoader.getValue(this.key));
    }
}
