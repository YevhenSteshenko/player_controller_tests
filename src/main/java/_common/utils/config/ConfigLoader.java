package _common.utils.config;

import lombok.NonNull;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;


public class ConfigLoader {
    private static final String ENVIRONMENT;
    private static final String API_VERSION;
    private static final Properties properties = new Properties();

    static {
        ENVIRONMENT = System.getProperty("env").toLowerCase();
        API_VERSION = System.getProperty("api_version").toLowerCase();

        System.out.println("Loading system properties");
        load("default.properties");
        load(ENVIRONMENT + "." + API_VERSION + ".properties");
    }

    private static void load(final String name) {
        System.out.println("Reading " + name);
        try {
            properties.load(new InputStreamReader(
                    Objects.requireNonNull(ConfigLoader.class.getClassLoader().getResourceAsStream(name)),
                    StandardCharsets.UTF_8)
            );
            System.out.println("Reading " + name + " done successfully");
        } catch (Throwable e) {
            System.out.println("Unable to read " + name + " cause is " + e.getMessage());
        }
    }

    static String getValue(@NonNull final String key) {
        if (!properties.containsKey(key)) {
            throw new ArrayIndexOutOfBoundsException("Key " + key + " is not configured");
        }
        return properties.getProperty(key);
    }
}
